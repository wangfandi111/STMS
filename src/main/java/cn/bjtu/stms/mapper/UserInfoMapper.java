package cn.bjtu.stms.mapper;

import cn.bjtu.stms.model.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserInfoMapper extends Mapper<UserInfo> {

    @Select("select * from user_info where user_role = #{userRole}")
    List<UserInfo> getUserInfoList(@Param("userRole") Integer userRole);

}