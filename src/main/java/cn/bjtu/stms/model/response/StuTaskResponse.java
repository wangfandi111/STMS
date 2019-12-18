package cn.bjtu.stms.model.response;

import cn.bjtu.stms.model.StuTask;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StuTaskResponse extends StuTask {

    private String stuName;

    private String hasSubmittedDes;

}