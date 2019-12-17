package cn.bjtu.stms.aop;

import cn.bjtu.stms.model.protocol.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

/**
 * 全局的异常处理
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandle {

    /**
     * 顶级的异常处理
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ResponseData handleException(HttpServletRequest req, Exception e) {
        printExceptionLog(req, e);
        return ResponseData.response(599, "服务异常", e.getMessage());
    }

    /**
     * 参数非法抛出的异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingServletRequestParameterException.class, HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class, BindException.class,
            ConstraintViolationException.class, ValidationException.class})
    public ResponseData handleParameterException(HttpServletRequest req, Exception e) {
        printExceptionLog(req, e);
        return ResponseData.failParamaters();
    }

    public static void printExceptionLog(HttpServletRequest req, Exception e) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("catch global exception of request ").append(req.getServletPath()).append(" and exception")
                .append(getErrorStackInfo(e));
        log.error(stringBuilder.toString());
    }

    public static String getErrorStackInfo(Exception e) {
        StringBuffer resBuffer = new StringBuffer();
        resBuffer.append("\n" + e.getMessage() + "\n");
        StackTraceElement[] megs = e.getStackTrace();
        for (int i = 0; i < megs.length; i++) {
            resBuffer.append(megs[i] + "\n");
        }
        return resBuffer.toString();
    }

}