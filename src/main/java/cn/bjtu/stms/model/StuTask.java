package cn.bjtu.stms.model;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "stu_task")
public class StuTask {
    @Id
    @Column(name = "task_id")
    private Integer taskId;

    @Id
    @Column(name = "stu_id")
    private Integer stuId;

    @Column(name = "has_submitted")
    private Integer hasSubmitted;

    @Column(name = "submit_time")
    private Date submitTime;

    @Column(name = "submit_text")
    private String submitText;

}