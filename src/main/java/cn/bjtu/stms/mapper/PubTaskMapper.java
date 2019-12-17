package cn.bjtu.stms.mapper;

import cn.bjtu.stms.model.PubTask;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PubTaskMapper extends Mapper<PubTask> {

    @Select("select * from pub_task where task_id = #{taskId} and teacher_id = #{teacherId}")
    PubTask getPubTaskByTaskId(@Param("taskId") Integer taskId, @Param("teacherId") Integer teacherId);

    @Select("select * from pub_task where teacher_id = #{teacherId} order by create_time desc limit #{limit} offset #{offset}")
    List<PubTask> getPubTaskListByTeacherId(@Param("teacherId") Integer teacherId, @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Select("select count(distinct(task_id)) from pub_task where teacher_id = #{teacherId}")
    Integer countPubTasksByTeacherId(@Param("teacherId") Integer teacherId);

}