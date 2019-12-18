package cn.bjtu.stms.mapper;

import cn.bjtu.stms.model.StuTask;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StuTaskMapper extends Mapper<StuTask> {

    @Select("select count(distinct(task_id)) from stu_task where stu_id = #{studentId} and has_submitted = #{submitStatus}")
    Integer countStuTasksByTaskStatus(@Param("studentId") Integer studentId, @Param("submitStatus") Integer submitStatus);

    @Select("select * from stu_task where task_id = #{taskId} and stu_id = #{studentId}")
    StuTask getStuTask(@Param("taskId") Integer taskId, @Param("studentId") Integer studentId);

    @Select("select distint(*) from stu_task where task_id = #{taskId} order by submit_time desc limit #{limit} offset #{offset}")
    List<StuTask> getStuTaskList(@Param("taskId") Integer taskId, @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Select("select ount(distint(stu_id)) from stu_task where task_id = #{taskId}")
    Integer countStuTasks(@Param("taskId") Integer taskId);

    @Select("select count(distint(stu_id)) from stu_task where task_id = #{taskId} and has_submitted = #{submitStatus}")
    Integer countStuTasksByStatus(@Param("taskId") Integer taskId, @Param("hasSubmitted") Integer submitStatus);

}