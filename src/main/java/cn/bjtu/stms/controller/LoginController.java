package cn.bjtu.stms.controller;

import cn.bjtu.stms.model.UserInfo;
import cn.bjtu.stms.model.protocol.ResponseData;
import cn.bjtu.stms.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("user")
public class LoginController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "登录", notes = "登录")
    @ApiImplicitParam(name = "jsonNode", value = "登录，例：{'loginStr':'teacher','password':'password'}",
            required = true, dataType = "string", paramType = "body")
    @PostMapping(value = "login")
    public ResponseData login(@RequestBody JsonNode jsonNode, HttpServletRequest request) {
        String loginStr = jsonNode.hasNonNull("loginStr") ? jsonNode.get("loginStr").textValue() : "";
        String password = jsonNode.hasNonNull("password") ? jsonNode.get("password").textValue() : "";
        HttpSession session = request.getSession(true);
        return userService.login(loginStr, password, session);
    }

    @ApiOperation(value = "注册", notes = "注册")
    @ApiImplicitParam(name = "userInfo", required = true, paramType = "body",
            value = "注册，例子：{'userName':'刘老板',\n'userJobid':'19121111',\n" +
                    "'userPassword':'userPassword',\n'user_role':'0/1'}\n(0代表老师，1代表学生)")
    @PostMapping(value = "register")
    public ResponseData register(@Valid @RequestBody UserInfo userInfo) {
        try {
            return userService.register(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.fail("注册失败！");
        }
    }

    @ApiOperation(value = "注销", notes = "注销")
    @PostMapping(value = "logout")
    public ResponseData layOut(@Valid @RequestBody UserInfo userInfo, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.removeAttribute("userInfo");
        return ResponseData.success("注销！");
    }

    @ApiOperation(value = "学生列表", notes = "学生列表")
    @PostMapping(value = "students")
    public ResponseData getStudentList() {
        return userService.getStudentList();
    }


}
