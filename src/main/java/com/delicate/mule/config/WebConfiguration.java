package com.delicate.mule.config;

import com.delicate.mule.common.intercepter.AdminAuthHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;


@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    AdminAuthHandlerInterceptor adminAuthHandlerInterceptor() {
        return new AdminAuthHandlerInterceptor();
    }


    List<String> excludePatterns = Arrays.asList("/login", "/reg", "/doc.html", "/swagger-resources/**", "/v2/**", "/error", "/favicon.ico", "/webjars/**");

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtHandlerInterceptor()).excludePathPatterns(excludePatterns);
        registry.addInterceptor(adminAuthHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns(excludePatterns);
    }
}
