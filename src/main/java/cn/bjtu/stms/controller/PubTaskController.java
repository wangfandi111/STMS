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
    @ApiImplicitParam(name = "jsonNode", value = "任务信息，例子：{\"taskName\":\"任务名称\",\"taskContent\":\"任务内容\"}",
            required = true, dataType = "string", paramType = "body")
    @PostMapping(value = "/task/pub")
    public ResponseData pubTask(@RequestBody JsonNode jsonNode, HttpServletRequest request) {
        String taskName = jsonNode.get("taskName").textValue();
        String taskContent = jsonNode.get("taskContent").textValue();
        HttpSession session = request.getSession(true);
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        return taskService.pubTask(userInfo, taskName, taskContent);
    }

}
