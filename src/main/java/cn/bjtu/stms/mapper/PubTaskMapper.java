package cn.bjtu.stms.mapper;

import cn.bjtu.stms.model.PubTask;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PubTaskMapper extends Mapper<PubTask> {

    @Select("select * from pub_task where task_id = #{taskId} and teacher_id = #{teacherId} and delete_tag = 0")
    PubTask getPubTaskByTaskId(@Param("taskId") Integer taskId, @Param("teacherId") Integer teacherId);

    @Select("select * from pub_task where teacher_id = #{teacherId} and delete_tag = 0 order by create_time desc limit #{limit} offset #{offset}")
    List<PubTask> getPubTaskListByTeacherId(@Param("teacherId") Integer teacherId, @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Select("select count(distinct(task_id)) from pub_task where teacher_id = #{teacherId} and delete_tag = 0")
    Integer countPubTasksByTeacherId(@Param("teacherId") Integer teacherId);

    @Select("select * from pub_task where delete_tag = 0 and task_id in " +
            "(select distinct(task_id)) from stu_task where stu_id = #{studentId} ) " +
            "order by create_time desc limit #{limit} offset #{offset}")
    List<PubTask> getPubTaskListByStudentId(@Param("studentId") Integer studentId, @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Select("select count(distinct(task_id)) from pub_task where delete_tag = 0 and task_id in " +
            "(select distinct(task_id)) from stu_task where stu_id = #{studentId} )")
    Integer countPubTasksByStudentId(@Param("studentId") Integer studentId);

    @Update("update pub_task set delete_tag = 1 where task_id = #{taskId} and teacher_id = #{teacherId} and task_status = #{taskStatus}")
    int deletePubTaskById(@Param("taskId") Integer taskId, @Param("teacherId") Integer teacherId, @Param("taskStatus") Integer taskStatus);

}