package cn.bjtu.stms.model.protocol;

import cn.bjtu.stms.constants.CodeEnum;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseData {
    Integer code;
    String msg;
    Object data;

    @Override
    public String toString() {
        JSONObject res = new JSONObject();
        res.put("code", code);
        res.put("message", msg);
        res.put("data", data);
        return res.toJSONString();
    }

    public static ResponseData response(CodeEnum codeEnum, Object data) {
        return new ResponseData(codeEnum.getCode(), codeEnum.getMsg(), data);
    }

    public static ResponseData response(Integer code, String msg, Object data) {
        return new ResponseData(code, msg, data);
    }

    public static ResponseData response(CodeEnum codeEnum) {
        return new ResponseData(codeEnum.getCode(), codeEnum.getMsg(), null);
    }

    public static ResponseData response(Integer code, String msg) {
        return new ResponseData(code, msg, null);
    }

    public static ResponseData success() {
        return ResponseData.response(CodeEnum.OK, null);
    }

    public static ResponseData success(String msg) {
        return new ResponseData(CodeEnum.OK.getCode(), msg, null);
    }

    public static ResponseData success(Object data) {
        return ResponseData.response(CodeEnum.OK, data);
    }

    public static ResponseData success(String msg, Object data) {
        return ResponseData.response(CodeEnum.OK.getCode(), msg, data);
    }

    /**
     * 无权限
     */
    public static ResponseData noPermission() {
        return ResponseData.response(CodeEnum.PRIVILEGE_NEED, null);
    }

    /**
     * 参数不合法
     */
    public static ResponseData failParamaters() {
        return ResponseData.response(CodeEnum.PARAM_ILLEGAL, null);
    }

    public static ResponseData failParamaters(String msg) {
        return ResponseData.response(CodeEnum.PARAM_ILLEGAL.getCode(), msg, null);
    }

    public static ResponseData failParamaters(Object data) {
        return ResponseData.response(CodeEnum.PARAM_ILLEGAL, data);
    }

    /**
     * 调用失败
     */
    public static ResponseData fail() {
        return ResponseData.response(CodeEnum.FAIL, null);
    }

    public static ResponseData fail(String msg) {
        return ResponseData.response(CodeEnum.FAIL.getCode(), msg, null);
    }

    public static ResponseData fail(Object data) {
        return ResponseData.response(CodeEnum.FAIL, data);
    }
}
