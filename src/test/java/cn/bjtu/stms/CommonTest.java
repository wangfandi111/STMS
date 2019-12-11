package cn.bjtu.stms;


import cn.bjtu.stms.utils.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class CommonTest {

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void test() throws IOException {
        System.out.println(StringUtil.isEmail("884078898@qq.com"));

    }

}
