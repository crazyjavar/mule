package com.delicate.mule.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    /**
     * 默认使用Jackson序列化
     * 关闭Jackson的FAIL_ON_EMPTY_BEANS
     * 因为对象中如果属性值为null的时候，序列化会报错InvalidDefinitionException
     *
     * @return
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }
}
