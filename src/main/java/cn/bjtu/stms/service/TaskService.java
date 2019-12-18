package cn.bjtu.stms.service;

import cn.bjtu.stms.constants.SubmitStatusEnum;
import cn.bjtu.stms.constants.TaskStatusEnum;
import cn.bjtu.stms.constants.UserRoleEnum;
import cn.bjtu.stms.mapper.PubTaskMapper;
import cn.bjtu.stms.mapper.StuTaskMapper;
import cn.bjtu.stms.mapper.UserInfoMapper;
import cn.bjtu.stms.model.PubTask;
import cn.bjtu.stms.model.StuTask;
import cn.bjtu.stms.model.UserInfo;
import cn.bjtu.stms.model.protocol.Pager;
import cn.bjtu.stms.model.protocol.ResponseData;
import cn.bjtu.stms.model.response.PubTaskResponse;
import cn.bjtu.stms.model.response.StuTaskResponse;
import cn.bjtu.stms.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service
public class TaskService {

    @Resource
    private PubTaskMapper pubTaskMapper;

    @Resource
    private StuTaskMapper stuTaskMapper;

    @Resource
    private UserInfoMapper userInfoMapper;


    public ResponseData getTaskStatistics(UserInfo userInfo) {
        if (userInfo == null)
            return ResponseData.fail("用户信息错误！");

        Map<String, Integer> resultMap = new HashMap();
        if (userInfo.getUserRole().equals(UserRoleEnum.TEACHER.getCode())) {
            int createTaskNum = pubTaskMapper.countPubTasksByTaskStatus(userInfo.getUserId(), TaskStatusEnum.CREATE.getCode());
            int doingTaskNum = pubTaskMapper.countPubTasksByTaskStatus(userInfo.getUserId(), TaskStatusEnum.DOING.getCode());
            int endTaskNum = pubTaskMapper.countPubTasksByTaskStatus(userInfo.getUserId(), TaskStatusEnum.END.getCode());
            resultMap.put("createTaskNum", createTaskNum);
            resultMap.put("doingTaskNum", doingTaskNum);
            resultMap.put("endTaskNum", endTaskNum);
        } else {
            int unSubmitNum = stuTaskMapper.countStuTasksByTaskStatus(userInfo.getUserId(), SubmitStatusEnum.UNSUBMIT.getCode());
            int submitNum = stuTaskMapper.countStuTasksByTaskStatus(userInfo.getUserId(), SubmitStatusEnum.SUBMIT.getCode());
            resultMap.put("unSubmitNum", unSubmitNum);
            resultMap.put("submitNum", submitNum);
        }
        return ResponseData.success(resultMap);
    }

    public ResponseData pubTask(UserInfo userInfo, String taskName, String taskContent) {
        if (userInfo == null || !userInfo.getUserRole().equals(UserRoleEnum.TEACHER.getCode()))
            return ResponseData.fail("用户无权限或信息错误！");
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
            return ResponseData.fail("用户无权限或信息错误！");
        if (taskId == null || StringUtil.isEmpty(taskName) || StringUtil.isEmpty(taskContent))
            return ResponseData.fail("任务信息错误！");
        PubTask pubTask = pubTaskMapper.getPubTaskByTaskId(taskId, userInfo.getUserId());
        if (pubTask == null)
            return ResponseData.fail("在该老师任务列表中未查询到此任务！");
        pubTask.setTaskName(taskName);
        pubTask.setTaskContent(taskContent);

        int id = pubTaskMapper.updateByPrimaryKeySelective(pubTask);
        return id > 0 ? ResponseData.success("修改任务成功！") : ResponseData.failParamaters("修改任务失败！");
    }

    public ResponseData deletePubTask(UserInfo userInfo, Integer taskId) {
        if (userInfo == null || !userInfo.getUserRole().equals(UserRoleEnum.TEACHER.getCode()))
            return ResponseData.fail("用户无权限或信息错误！");
        if (taskId == null)
            return ResponseData.fail("任务信息错误！");
        int id = pubTaskMapper.deletePubTaskById(taskId, userInfo.getUserId(), TaskStatusEnum.CREATE.getCode());
        return id > 0 ? ResponseData.success("删除任务成功！") : ResponseData.failParamaters("删除任务失败！");
    }

    public ResponseData getPubTaskById(Integer taskId) {
        if (taskId == null)
            return ResponseData.fail("任务信息错误！");
        PubTask pubTask = pubTaskMapper.selectByPrimaryKey(taskId);
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
        List<PubTaskResponse> pubTaskResponsesList = new ArrayList<>();
        for (PubTask pubTask : pubTaskList) {
            PubTaskResponse pubTaskResponse = new PubTaskResponse();
            BeanUtils.copyProperties(pubTask, pubTaskResponse);
            pubTaskResponse.setTaskStatusDes(TaskStatusEnum.getContentByCode(pubTask.getTaskStatus()));
            pubTaskResponsesList.add(pubTaskResponse);
        }
        Pager data = new Pager(pubTaskResponsesList, pageNo, pageSize, total);
        return ResponseData.success(data);
    }

    public ResponseData distributeTask(UserInfo userInfo, Integer taskId, List<Integer> studentIdList) {
        if (userInfo == null)
            return ResponseData.fail("用户信息错误！");
        if (taskId == null || studentIdList == null || studentIdList.isEmpty())
            return ResponseData.fail("任务信息或学生信息错误！");

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

    public ResponseData remarkTask(UserInfo userInfo, Integer taskId, String remarkText) {
        if (userInfo == null || !userInfo.getUserRole().equals(UserRoleEnum.TEACHER.getCode()))
            return ResponseData.fail("用户无权限或信息错误！");
        if (taskId == null || StringUtil.isEmpty(remarkText))
            return ResponseData.fail("任务信息错误！");

        PubTask pubTask = pubTaskMapper.getPubTaskByTaskId(taskId, userInfo.getUserId());
        if (pubTask == null)
            return ResponseData.fail("在该老师任务列表中未查询到此任务！");
        pubTask.setRemarkText(remarkText);

        int id = pubTaskMapper.updateByPrimaryKeySelective(pubTask);
        return id > 0 ? ResponseData.success("点评成功！") : ResponseData.failParamaters("点评失败！");
    }


    public ResponseData getPubTaskDetail(Integer taskId, Integer pageNo, Integer pageSize) {
        Integer limit = Pager.getValidPageSize(pageSize, 10);
        Integer offset = Pager.getOffset(pageNo, pageSize);

        Integer total = stuTaskMapper.countStuTasks(taskId);
        List<StuTask> stuTaskList = stuTaskMapper.getStuTaskList(taskId, limit, offset);
        List<StuTaskResponse> stuTaskResponseList = new ArrayList<>();
        for (StuTask stuTask : stuTaskList) {
            StuTaskResponse stuTaskResponse = new StuTaskResponse();
            BeanUtils.copyProperties(stuTask, stuTaskResponse);
            String stuName = userInfoMapper.selectByPrimaryKey(stuTask.getStuId()).getUserName();
            stuTaskResponse.setStuName(stuName);
            stuTaskResponse.setHasSubmittedDes(SubmitStatusEnum.getContentByCode(stuTask.getHasSubmitted()));
            stuTaskResponseList.add(stuTaskResponse);
        }
        Pager data = new Pager(stuTaskResponseList, pageNo, pageSize, total);
        return ResponseData.success(data);
    }

    public ResponseData submitTask(UserInfo userInfo, Integer taskId, String submitText) {
        if (userInfo == null || !userInfo.getUserRole().equals(UserRoleEnum.STUDENT.getCode()))
            return ResponseData.fail("用户信息错误！");
        if (taskId == null || StringUtil.isEmpty(submitText))
            return ResponseData.fail("任务信息错误！");
        StuTask stuTask = stuTaskMapper.getStuTask(taskId, userInfo.getUserId());
        stuTask.setHasSubmitted(SubmitStatusEnum.SUBMIT.getCode());
        stuTask.setSubmitText(submitText);
        stuTask.setSubmitTime(new Date());
        stuTaskMapper.updateByPrimaryKeySelective(stuTask);

        Integer unSubmitNum = stuTaskMapper.countStuTasksByStatus(taskId, SubmitStatusEnum.UNSUBMIT.getCode());
        if (unSubmitNum == 0) {
            PubTask pubTask = pubTaskMapper.selectByPrimaryKey(taskId);
            pubTask.setTaskStatus(TaskStatusEnum.END.getCode());
            pubTaskMapper.updateByPrimaryKeySelective(pubTask);
        }
        return ResponseData.success();
    }
}
