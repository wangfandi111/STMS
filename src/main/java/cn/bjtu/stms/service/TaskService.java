package cn.bjtu.stms.service;

import cn.bjtu.stms.constants.UserRoleEnum;
import cn.bjtu.stms.mapper.PubTaskMapper;
import cn.bjtu.stms.model.PubTask;
import cn.bjtu.stms.model.UserInfo;
import cn.bjtu.stms.model.protocol.Pager;
import cn.bjtu.stms.model.protocol.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class TaskService {

    @Resource
    private PubTaskMapper pubTaskMapper;

    public ResponseData pubTask(UserInfo userInfo, String taskName, String taskContent) {
        if (userInfo == null || !userInfo.getUserRole().equals(UserRoleEnum.TEACHER.getCode()))
            return ResponseData.fail("用户信息错误！");
        PubTask pubTask = new PubTask();
        pubTask.setTaskName(taskName);
        pubTask.setTaskContent(taskContent);
        pubTask.setTaskStatus(0);
        pubTask.setTeacherId(userInfo.getUserId());

        int id = pubTaskMapper.insertSelective(pubTask);
        return id > 0 ? ResponseData.success("发布任务成功！") : ResponseData.failParamaters("发布任务失败！");
    }

    public ResponseData getTaskById(UserInfo userInfo, Integer taskId) {
        if (userInfo == null || !userInfo.getUserRole().equals(UserRoleEnum.TEACHER.getCode()))
            return ResponseData.fail("用户信息错误！");
        PubTask pubTask = pubTaskMapper.getPubTaskByTaskId(taskId, userInfo.getUserId());
        return pubTask != null ? ResponseData.success(pubTask) : ResponseData.failParamaters("未查询到该任务");
    }

    public ResponseData getTaskByTeacher(UserInfo userInfo, Integer pageNo, Integer pageSize) {
        if (userInfo == null || !userInfo.getUserRole().equals(UserRoleEnum.TEACHER.getCode()))
            return ResponseData.fail("用户信息错误！");
        Integer limit = Pager.getValidPageSize(pageSize, 10);
        Integer offset = Pager.getOffset(pageNo, pageSize);
        Integer total = pubTaskMapper.countPubTasksByTeacherId(userInfo.getUserId());
        List<PubTask> pubTaskList = pubTaskMapper.getPubTaskListByTeacherId(userInfo.getUserId(), limit, offset);
        Pager data = new Pager(pubTaskList, pageNo, pageSize, total);
        return pubTaskList != null ? ResponseData.success(data) : ResponseData.failParamaters("任务列表为空");
    }

}
