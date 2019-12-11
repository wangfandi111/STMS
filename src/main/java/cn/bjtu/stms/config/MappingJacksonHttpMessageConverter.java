/*
package cn.bjtu.papercheck.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class MappingJacksonHttpMessageConverter extends MappingJackson2HttpMessageConverter {

    public MappingJacksonHttpMessageConverter() {
        super();
        this.setSelfConfiguration();
    }

    public MappingJacksonHttpMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper);
        this.setSelfConfiguration();
    }

    private void setSelfConfiguration() {
        // 任何属性可见
        super.getObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        // 屏蔽get方法
        super.getObjectMapper().setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        // 屏蔽null
        super.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }


}
*/
