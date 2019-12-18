package cn.bjtu.stms.controller;

import cn.bjtu.stms.model.UserInfo;
import cn.bjtu.stms.model.protocol.ResponseData;
import cn.bjtu.stms.service.TaskService;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.xdevapi.JsonArray;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @ApiOperation(value = "发布任务", notes = "老师发布任务")
    @ApiImplicitParam(name = "jsonNode", value = "任务信息，例：{'taskName':'任务名称','taskContent':'任务内容'}",
            required = true, dataType = "string", paramType = "body")
    @PostMapping(value = "/pub")
    public ResponseData pubTask(@RequestBody JsonNode jsonNode, HttpServletRequest request) {
        String taskName = jsonNode.get("taskName").textValue();
        String taskContent = jsonNode.get("taskContent").textValue();
        HttpSession session = request.getSession(true);
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        return taskService.pubTask(userInfo, taskName, taskContent);
    }

    @ApiOperation(value = "修改某一任务", notes = "老师修改某一任务")
    @ApiImplicitParam(name = "jsonNode", value = "任务信息，例：{'taskId':2,'taskName':'任务名称','taskContent':'任务内容'}",
            required = true, dataType = "string", paramType = "body")
    @PostMapping(value = "/modify")
    public ResponseData modifyPubTask(@RequestBody JsonNode jsonNode, HttpServletRequest request) {
        Integer taskId = jsonNode.hasNonNull("taskId") ? jsonNode.get("taskId").intValue() : null;
        String taskName = jsonNode.hasNonNull("taskName") ? jsonNode.get("taskName").textValue() : null;
        String taskContent = jsonNode.hasNonNull("taskContent") ? jsonNode.get("taskContent").textValue() : null;
        HttpSession session = request.getSession(true);
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        return taskService.modifyPubTask(userInfo, taskId, taskName, taskContent);
    }

    @ApiOperation(value = "删除某一任务", notes = "老师删除某一任务")
    @ApiImplicitParam(name = "jsonNode", value = "任务信息，例：{'taskId':2}",
            required = true, dataType = "string", paramType = "body")
    @PostMapping(value = "/delete")
    public ResponseData deletePubTask(@RequestBody JsonNode jsonNode, HttpServletRequest request) {
        Integer taskId = jsonNode.hasNonNull("taskId") ? jsonNode.get("taskId").intValue() : null;
        HttpSession session = request.getSession(true);
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        return taskService.deletePubTask(userInfo, taskId);
    }

    @ApiOperation(value = "查询某一任务", notes = "老师查询某一任务")
    @ApiImplicitParam(name = "jsonNode", value = "任务id，例：{'taskId':2}",
            required = true, dataType = "string", paramType = "body")
    @PostMapping(value = "/search")
    public ResponseData getTaskById(@RequestBody JsonNode jsonNode, HttpServletRequest request) {
        Integer taskId = jsonNode.hasNonNull("taskId") ? jsonNode.get("taskId").intValue() : null;
        HttpSession session = request.getSession(true);
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        return taskService.getPubTaskById(userInfo, taskId);
    }

    @ApiOperation(value = "查询任务列表", notes = "查询任务列表")
    @ApiImplicitParam(name = "jsonNode", value = "任务id，例：{'pageNo':1,'pageSize':10}",
            required = false, dataType = "string", paramType = "body")
    @PostMapping(value = "/all")
    public ResponseData getPubTaskList(@RequestBody(required = false) JsonNode jsonNode, HttpServletRequest request) {
        Integer pageNo = jsonNode != null && jsonNode.hasNonNull("pageNo") ? jsonNode.get("pageNo").intValue() : 1;
        Integer pageSize = jsonNode != null && jsonNode.hasNonNull("pageSize") ? jsonNode.get("pageSize").intValue() : 10;
        HttpSession session = request.getSession(true);
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        return taskService.getPubTaskList(userInfo, pageNo, pageSize);
    }


    @ApiOperation(value = "分配任务", notes = "老师分配任务")
    @ApiImplicitParam(name = "jsonNode", value = "任务id与学生id列表，例：{'taskId':1,'student':[{'studentId': 15555557},{'studentId': 15555557}]}",
            required = true, dataType = "string", paramType = "body")
    @PostMapping(value = "/distribute")
    public ResponseData distributeTask(@RequestBody JsonNode jsonNode, HttpServletRequest request) {
        Integer taskId = jsonNode.hasNonNull("taskId") ? jsonNode.get("taskId").intValue() : null;
        JsonNode studentIdListNode = jsonNode.hasNonNull("student") ? jsonNode.get("student") : null;
        List<Integer> studentIdList = new LinkedList<>();
        try {
            if (studentIdListNode.isArray()) {
                for (JsonNode node : studentIdListNode)
                    studentIdList.add(node.get("studentId").intValue());
            }
        } catch (Exception e) {
            return ResponseData.fail("请输入正确的学生学号列表格式");
        }
        HttpSession session = request.getSession(true);
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        return taskService.distributeTask(userInfo, taskId, studentIdList);
    }

}