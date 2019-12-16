package cn.bjtu.stms.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "stu_task")
public class StuTask {
    @Id
    @Column(name = "task_id")
    private Integer taskId;

    @Id
    @Column(name = "stu_id")
    private Integer stuId;

    @Column(name = "has_submitted")
    private Byte hasSubmitted;

    @Column(name = "submit_time")
    private Date submitTime;

    @Column(name = "submit_text")
    private String submitText;

    @Column(name = "remark_text")
    private String remarkText;

    /**
     * @return task_id
     */
    public Integer getTaskId() {
        return taskId;
    }

    /**
     * @param taskId
     */
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    /**
     * @return stu_id
     */
    public Integer getStuId() {
        return stuId;
    }

    /**
     * @param stuId
     */
    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    /**
     * @return has_submitted
     */
    public Byte getHasSubmitted() {
        return hasSubmitted;
    }

    /**
     * @param hasSubmitted
     */
    public void setHasSubmitted(Byte hasSubmitted) {
        this.hasSubmitted = hasSubmitted;
    }

    /**
     * @return submit_time
     */
    public Date getSubmitTime() {
        return submitTime;
    }

    /**
     * @param submitTime
     */
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    /**
     * @return submit_text
     */
    public String getSubmitText() {
        return submitText;
    }

    /**
     * @param submitText
     */
    public void setSubmitText(String submitText) {
        this.submitText = submitText;
    }

    /**
     * @return remark_text
     */
    public String getRemarkText() {
        return remarkText;
    }

    /**
     * @param remarkText
     */
    public void setRemarkText(String remarkText) {
        this.remarkText = remarkText;
    }
}