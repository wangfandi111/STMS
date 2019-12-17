package cn.bjtu.stms.mapper;

import cn.bjtu.stms.model.PubTask;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface PubTaskMapper extends Mapper<PubTask> {
	public List<PubTask> selectAllTasks();
	public void createTask(PubTask task);
	public void updatePubTask(PubTask record);
}