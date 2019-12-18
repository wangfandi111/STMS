package cn.bjtu.stms.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HealthController {

    @ApiOperation(value = "检查服务可用", notes = "检查服务可用")
    @RequestMapping(method = RequestMethod.GET, path = "/health")
    @ResponseBody
    public String health() {
        return "hello world!!!";
    }

}
