//package cn.bjtu.stms.service;
//
//import cn.bjtu.stms.model.protocol.ResponseData;
//import cn.bjtu.stms.utils.StringUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//
//@Slf4j
//@Service
//public class UserService {
//
//    @Resource
//    private TUserMapper tUserMapper;
//
//    public ResponseData login(String userNameStr, String password) {
//        TUser tUser = new TUser();
//        if (StringUtil.isEmail(userNameStr)) {
//            tUser.setUEmail(userNameStr);
//        } else if (StringUtil.isTelephone(userNameStr)) {
//            tUser.setUTelephone(userNameStr);
//        } else {
//            tUser.setUName(userNameStr);
//        }
//        tUser = tUserMapper.selectOne(tUser);
//        if (tUser != null && tUser.getUPassword().equals(password)) {
//            return ResponseData.success("登录成功！", tUser);
//        }
//        return ResponseData.fail("请输入正确的用户名或密码！");
//    }
//
//    public ResponseData register(TUser tUser) throws Exception {
//        if (tUser != null && StringUtil.isEmail(tUser.getUEmail())) {
//            int id = tUserMapper.insertSelective(tUser);
//            return id > 0 ? ResponseData.success("注册成功") : ResponseData.failParamaters("注册失败！");
//        } else {
//            return ResponseData.failParamaters();
//        }
//    }
//
//}
