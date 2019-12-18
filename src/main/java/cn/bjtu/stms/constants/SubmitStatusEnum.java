package cn.bjtu.stms.constants;

import java.util.LinkedHashMap;
import java.util.Map;

public enum SubmitStatusEnum {

    UNSUBMIT(0, "未提交"),
    SUBMIT(1, "已提交");

    private Integer code;
    private String content;

    SubmitStatusEnum(Integer code, String content) {
        this.code = code;
        this.content = content;
    }

    public static final Map<Integer, String> UserRoleMap = new LinkedHashMap<>();

    static {
        for (SubmitStatusEnum p : SubmitStatusEnum.values()) {
            UserRoleMap.put(p.getCode(), p.getContent());
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static String getContentByCode(Integer code) {
        for (SubmitStatusEnum submitStatusEnum : SubmitStatusEnum.values()) {
            if (code.equals(submitStatusEnum.getCode())) {
                return submitStatusEnum.getContent();
            }
        }
        return null;
    }
}
