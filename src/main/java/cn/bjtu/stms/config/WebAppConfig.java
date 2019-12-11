package cn.bjtu.stms.config;

import cn.bjtu.stms.interceptor.LoginInterceptor;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/*").
                excludePathPatterns("/health", "/test/**");
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // 不序列化null的属性
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")); // 默认的时间序列化格式
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY); // 任何属性可见
        mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE); // 屏蔽get方法
        return mapper;
    }

}
