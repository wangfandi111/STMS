package cn.bjtu.stms.controller.user;

import cn.bjtu.stms.model.protocol.ResponseData;
import cn.bjtu.stms.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("user")
public class LoginController {

    @Autowired
    private UserService userService;


    @PostMapping(value = "login")
    public ResponseData login(@RequestBody JsonNode jsonNode) {
        String userNameStr = jsonNode.get("userNameStr").textValue();
        String password= jsonNode.get("password").textValue();
        return userService.login(userNameStr, password);
    }

    @PostMapping(value = "register")
    public ResponseData register(@Valid @RequestBody TUser tUser) {
        try {
            return userService.register(tUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.fail("注册失败！");
        }
    }


}
