package cn.bjtu.stms.model.protocol;

import cn.bjtu.stms.constants.CodeEnum;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class PaperCheckException extends RuntimeException {
    private int code;
    private String msg;

    public PaperCheckException(int code, String msg) {
        super(String.format("type:%s, message:%s", code, msg));
        this.code = code;
        this.msg = msg;
    }

    public PaperCheckException(CodeEnum codeEnum) {
        this(codeEnum.getCode(), codeEnum.getMsg());
    }

}
