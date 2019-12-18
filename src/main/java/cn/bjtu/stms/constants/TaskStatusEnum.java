package cn.bjtu.stms.constants;

import java.util.LinkedHashMap;
import java.util.Map;

public enum TaskStatusEnum {

    CREATE(0, "创建中"),
    DOING(1, "进行中"),
    END(2, "已完成");

    private Integer code;
    private String content;

    TaskStatusEnum(Integer code, String content) {
        this.code = code;
        this.content = content;
    }

    public static final Map<Integer, String> UserRoleMap = new LinkedHashMap<>();

    static {
        for (TaskStatusEnum p : TaskStatusEnum.values()) {
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
        for (TaskStatusEnum taskStatusEnum : TaskStatusEnum.values()) {
            if (code.equals(taskStatusEnum.getCode())) {
                return taskStatusEnum.getContent();
            }
        }
        return null;
    }

}
