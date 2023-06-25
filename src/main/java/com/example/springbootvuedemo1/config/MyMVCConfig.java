package com.example.springbootvuedemo1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMVCConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)//允许携带cookie
                .allowedOriginPatterns("*")//允许所有域名访问
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")//允许所有请求方式访问
                .allowedHeaders("*")//允许所有请求头访问
                .exposedHeaders("*");//允许token访问
    }
}
