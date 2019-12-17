package cn.bjtu.stms.controller;

import cn.bjtu.stms.model.UserInfo;
import cn.bjtu.stms.model.protocol.ResponseData;
import cn.bjtu.stms.service.TaskService;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("teacher")
public class PubTaskController {

    @Autowired
    private TaskService taskService;

    @ApiOperation(value = "发布任务", notes = "老师发布任务")
    @ApiImplicitParam(name = "jsonNode", value = "任务信息，例：{'taskName':'任务名称','taskContent':'任务内容'}",
            required = true, dataType = "string", paramType = "body")
    @PostMapping(value = "/task/pub")
    public ResponseData pubTask(@RequestBody JsonNode jsonNode, HttpServletRequest request) {
        String taskName = jsonNode.get("taskName").textValue();
        String taskContent = jsonNode.get("taskContent").textValue();
        HttpSession session = request.getSession(true);
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        return taskService.pubTask(userInfo, taskName, taskContent);
    }

    @ApiOperation(value = "查询某一任务", notes = "老师查询某一任务")
    @ApiImplicitParam(name = "jsonNode", value = "任务id，例：{'taskId':2}",
            required = true, dataType = "string", paramType = "body")
    @PostMapping(value = "/task/search")
    public ResponseData getTaskById(@RequestBody JsonNode jsonNode, HttpServletRequest request) {
        Integer taskId = jsonNode.get("taskId").intValue();
        HttpSession session = request.getSession(true);
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        return taskService.getTaskById(userInfo, taskId);
    }

    @ApiOperation(value = "查询老师任务列表", notes = "查询老师任务列表")
    @ApiImplicitParam(name = "jsonNode", value = "任务id，例：{'pageNo':1,'pageSize':10}",
            required = true, dataType = "string", paramType = "body")
    @PostMapping(value = "/task/all")
    public ResponseData getTaskByTeacher(@RequestBody JsonNode jsonNode, HttpServletRequest request) {
        Integer pageNo = jsonNode.get("pageNo").intValue();
        Integer pageSize = jsonNode.get("pageSize").intValue();
        HttpSession session = request.getSession(true);
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        return taskService.getTaskByTeacher(userInfo, pageNo, pageSize);
    }

}
