package cn.bjtu.stms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "pub_task")
public class PubTask {
    @Id
    @Column(name = "task_id")
    private Integer taskId;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "task_content")
    private String taskContent;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    @Column(name = "task_status")
    private Integer taskStatus;

    private String taskStatusDes;

    @Column(name = "teacher_id")
    private Integer teacherId;

    @Column(name = "remark_text")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String remarkText;

    @Column(name = "delete_tag")
    private Integer deleteTag;
}