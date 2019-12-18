package cn.bjtu.stms.service;

import cn.bjtu.stms.constants.UserRoleEnum;
import cn.bjtu.stms.mapper.UserInfoMapper;
import cn.bjtu.stms.model.UserInfo;
import cn.bjtu.stms.model.protocol.Pager;
import cn.bjtu.stms.model.protocol.ResponseData;
import cn.bjtu.stms.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Service
public class UserService {

    @Resource
    private UserInfoMapper userInfoMapper;

    public ResponseData login(String loginStr, String password, HttpSession session) {
        UserInfo userInfo = new UserInfo();
        if (StringUtil.isPositiveNumeric(loginStr)) {
            userInfo.setUserJobid(loginStr);
            userInfo = userInfoMapper.selectOne(userInfo);
            if (userInfo != null && userInfo.getUserPassword().equals(password)) {
                session.setAttribute("userInfo", userInfo);
                return ResponseData.success("登录成功！", userInfo);
            }
        }
        return ResponseData.fail("请输入正确的用户名或密码！");
    }

    public ResponseData register(UserInfo userInfo) throws Exception {
        if (userInfo != null && StringUtil.isPositiveNumeric(userInfo.getUserJobid())) {
            userInfo.setUserId(null);
            int id = userInfoMapper.insertSelective(userInfo);
            return id > 0 ? ResponseData.success("注册成功") : ResponseData.failParamaters("注册失败！");
        } else {
            return ResponseData.failParamaters();
        }
    }

    public ResponseData getStudentList(Integer pageNo, Integer pageSize) {
        Integer limit = Pager.getValidPageSize(pageSize, 10);
        Integer offset = Pager.getOffset(pageNo, pageSize);

        Integer total = userInfoMapper.countUserInfosByRole(UserRoleEnum.STUDENT.getCode());
        List<UserInfo> userInfoList = userInfoMapper.getUserInfoListByRole(UserRoleEnum.STUDENT.getCode(), limit, offset);
        Pager data = new Pager(userInfoList, pageNo, pageSize, total) ;
        return ResponseData.success(data);
    }

}
