package cn.bjtu.stms.interceptor;

import cn.bjtu.stms.constants.CodeEnum;
import cn.bjtu.stms.model.UserInfo;
import cn.bjtu.stms.model.protocol.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


@Component
public class LoginInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
            throws Exception {
        HttpSession session = httpServletRequest.getSession(true);
        if (session != null && ((UserInfo) session.getAttribute("userInfo")) != null)
            return true;
        logger.info("未登录");

        PrintWriter writer = null;
        try {
            writer = httpServletResponse.getWriter();
            writer.print(ResponseData.response(CodeEnum.LOGIN_NEED));
        } catch (IOException e) {
            logger.error("response error",e);
        } finally {
            if (writer != null)
                writer.close();
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}

