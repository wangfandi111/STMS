package cn.bjtu.stms.mapper;

import cn.bjtu.stms.model.StuTask;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface StuTaskMapper extends Mapper<StuTask> {
	public void createStuTasks(List<StuTask> tasks);
	public List<StuTask> selectByStuId(Integer stuId);
	public void updateStuTask(StuTask record);
}