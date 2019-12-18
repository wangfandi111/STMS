package cn.bjtu.stms.mapper;

import cn.bjtu.stms.model.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserInfoMapper extends Mapper<UserInfo> {

    @Select("select * from user_info where user_role = #{userRole} order by user_id asc limit #{limit} offset #{offset}")
    List<UserInfo> getUserInfoListByRole(@Param("userRole") Integer userRole, @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Select("select count(distinct(user_id)) from user_info where user_role = #{userRole}")
    Integer countUserInfosByRole(@Param("userRole") Integer userRole);

}