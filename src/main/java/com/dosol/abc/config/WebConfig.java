package com.dosol.abc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // "/upload/**" 요청을 "file:C:/upload/" 폴더로 매핑
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:C:/upload/");
    }
}
