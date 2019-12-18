package cn.bjtu.stms.model.response;

import cn.bjtu.stms.model.PubTask;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PubTaskResponse extends PubTask {
    private String taskStatusDes;
}