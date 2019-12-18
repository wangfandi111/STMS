package cn.bjtu.stms.mapper;

import cn.bjtu.stms.model.StuTask;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface StuTaskMapper extends Mapper<StuTask> {

    @Select("select count(distinct(task_id)) from pub_task where student_id = #{studentId}")
    Integer countPubTasksByTeacherId(@Param("studentId") Integer studentId);

}