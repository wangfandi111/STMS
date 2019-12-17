package cn.bjtu.stms.mapper;

import cn.bjtu.stms.model.UserInfo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface UserInfoMapper extends Mapper<UserInfo> {

    @Select("select * from user_info where user_jobid = #{userJobid}")
    UserInfo getUserInfoByUserJobid(String userJobid);

}