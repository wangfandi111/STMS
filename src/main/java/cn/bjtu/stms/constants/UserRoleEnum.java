package cn.bjtu.stms.constants;

import java.util.LinkedHashMap;
import java.util.Map;

public enum UserRoleEnum {

    SUPER_ADMIN(0, "老师"),
    SCHOOL_ADMIN(1, "学生");

    private Integer code;
    private String content;

    UserRoleEnum(Integer code, String content) {
        this.code = code;
        this.content = content;
    }

    public static final Map<Integer, String> UserRoleMap = new LinkedHashMap<>();

    static {
        for (UserRoleEnum p : UserRoleEnum.values()) {
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

}
