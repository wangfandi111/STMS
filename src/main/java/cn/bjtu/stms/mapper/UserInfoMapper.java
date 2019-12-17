package cn.bjtu.stms.mapper;

import cn.bjtu.stms.model.UserInfo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface UserInfoMapper extends Mapper<UserInfo> {
	public UserInfo selectByUserName(String userName);
	public UserInfo selectByUserId(Integer userId);
	public List<UserInfo> selectByRole(Integer userRole);
}