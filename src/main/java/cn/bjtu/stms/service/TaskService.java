package cn.bjtu.stms.service;

import cn.bjtu.stms.constants.SubmitStatusEnum;
import cn.bjtu.stms.constants.TaskStatusEnum;
import cn.bjtu.stms.constants.UserRoleEnum;
import cn.bjtu.stms.mapper.PubTaskMapper;
import cn.bjtu.stms.mapper.StuTaskMapper;
import cn.bjtu.stms.model.PubTask;
import cn.bjtu.stms.model.StuTask;
import cn.bjtu.stms.model.UserInfo;
import cn.bjtu.stms.model.protocol.Pager;
import cn.bjtu.stms.model.protocol.ResponseData;
import cn.bjtu.stms.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class TaskService {

    @Resource
    private PubTaskMapper pubTaskMapper;

    @Resource
    private StuTaskMapper stuTaskMapper;

    public ResponseData pubTask(UserInfo userInfo, String taskName, String taskContent) {
        if (userInfo == null || !userInfo.getUserRole().equals(UserRoleEnum.TEACHER.getCode()))
            return ResponseData.fail("用户信息错误！");
        PubTask pubTask = new PubTask();
        pubTask.setTaskName(taskName);
        pubTask.setTaskContent(taskContent);
        pubTask.setTaskStatus(0);
        pubTask.setDeleteTag(TaskStatusEnum.CREATE.getCode());
        pubTask.setTeacherId(userInfo.getUserId());

        int id = pubTaskMapper.insertSelective(pubTask);
        return id > 0 ? ResponseData.success("发布任务成功！") : ResponseData.failParamaters("发布任务失败！");
    }

    public ResponseData modifyPubTask(UserInfo userInfo, Integer taskId, String taskName, String taskContent) {
        if (userInfo == null || !userInfo.getUserRole().equals(UserRoleEnum.TEACHER.getCode()))
            return ResponseData.fail("用户信息错误！");
        if (taskId == null || StringUtil.isEmpty(taskName) || StringUtil.isEmpty(taskContent))
            return ResponseData.fail("任务信息错误！");
        PubTask pubTask = new PubTask();
        pubTask.setTaskId(taskId);
        pubTask.setTaskName(taskName);
        pubTask.setTaskContent(taskContent);

        int id = pubTaskMapper.updateByPrimaryKeySelective(pubTask);
        return id > 0 ? ResponseData.success("修改任务成功！") : ResponseData.failParamaters("修改任务失败！");
    }

    public ResponseData deletePubTask(UserInfo userInfo, Integer taskId) {
        if (userInfo == null || !userInfo.getUserRole().equals(UserRoleEnum.TEACHER.getCode()))
            return ResponseData.fail("用户信息错误！");
        if (taskId == null)
            return ResponseData.fail("任务信息错误！");
        int id = pubTaskMapper.deletePubTaskById(taskId, userInfo.getUserId(), TaskStatusEnum.CREATE.getCode());
        return id > 0 ? ResponseData.success("删除任务成功！") : ResponseData.failParamaters("删除任务失败！");
    }

    public ResponseData getPubTaskById(UserInfo userInfo, Integer taskId) {
        if (userInfo == null || !userInfo.getUserRole().equals(UserRoleEnum.TEACHER.getCode()))
            return ResponseData.fail("用户信息错误！");
        PubTask pubTask = pubTaskMapper.getPubTaskByTaskId(taskId, userInfo.getUserId());
        return pubTask != null ? ResponseData.success(pubTask) : ResponseData.failParamaters("未查询到该任务");
    }

    public ResponseData getPubTaskList(UserInfo userInfo, Integer pageNo, Integer pageSize) {
        if (userInfo == null)
            return ResponseData.fail("用户信息错误！");
        Integer limit = Pager.getValidPageSize(pageSize, 10);
        Integer offset = Pager.getOffset(pageNo, pageSize);

        Integer total = 0;
        List<PubTask> pubTaskList = null;
        if (userInfo.getUserRole().equals(UserRoleEnum.TEACHER.getCode())) {
            total = pubTaskMapper.countPubTasksByTeacherId(userInfo.getUserId());
            pubTaskList = pubTaskMapper.getPubTaskListByTeacherId(userInfo.getUserId(), limit, offset);
        } else {
            total = pubTaskMapper.countPubTasksByStudentId(userInfo.getUserId());
            pubTaskList = pubTaskMapper.getPubTaskListByStudentId(userInfo.getUserId(), limit, offset);
        }
        for (PubTask pubTask : pubTaskList) {
            pubTask.setTaskStatusDes(TaskStatusEnum.getContentByCode(pubTask.getTaskStatus()));
        }
        Pager data = new Pager(pubTaskList, pageNo, pageSize, total);
        return ResponseData.success(data);
    }

    public ResponseData distributeTask(UserInfo userInfo, Integer taskId, List<Integer> studentIdList) {
        if (userInfo == null)
            return ResponseData.fail("用户信息错误！");
        if (taskId == null || studentIdList == null || studentIdList.isEmpty())
            return ResponseData.fail("任务信息或学生错误！");

        PubTask pubTask = pubTaskMapper.getPubTaskByTaskId(taskId, userInfo.getUserId());
        if (pubTask == null)
            return ResponseData.fail("未查询到该任务！");
        if (pubTask.getTaskStatus().equals(TaskStatusEnum.END.getCode())) {
            return ResponseData.fail("该任务已结束，无法分配任务！");
        }
        int insertNum = 0;
        for (Integer studentId : studentIdList) {
            StuTask stuTask = new StuTask();
            stuTask.setTaskId(taskId);
            stuTask.setHasSubmitted(SubmitStatusEnum.UNSUBMIT.getCode());
            stuTask.setStuId(studentId);
            insertNum += stuTaskMapper.insertSelective(stuTask);
        }
        if (insertNum > 0 && pubTask.getTaskStatus().equals(TaskStatusEnum.CREATE.getCode())) {
            pubTask.setTaskStatus(TaskStatusEnum.DOING.getCode());
            pubTaskMapper.updateByPrimaryKeySelective(pubTask);
        }
        return ResponseData.success();
    }
}
