package cn.bjtu.stms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ValidationException;

@Controller
public class HealthController {

    @RequestMapping(method = RequestMethod.GET, path = "/health")
    @ResponseBody
    public String health() {
        return "hello world!!!";
    }

}
