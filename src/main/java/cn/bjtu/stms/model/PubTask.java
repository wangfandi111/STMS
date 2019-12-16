package cn.bjtu.stms.model;

import java.util.Date;
import javax.persistence.*;

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

    @Column(name = "teacher_id")
    private Integer teacherId;

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
     * @return task_name
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * @param taskName
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * @return task_content
     */
    public String getTaskContent() {
        return taskContent;
    }

    /**
     * @param taskContent
     */
    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return modify_time
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * @param modifyTime
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * @return task_status
     */
    public Integer getTaskStatus() {
        return taskStatus;
    }

    /**
     * @param taskStatus
     */
    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    /**
     * @return teacher_id
     */
    public Integer getTeacherId() {
        return teacherId;
    }

    /**
     * @param teacherId
     */
    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }
}