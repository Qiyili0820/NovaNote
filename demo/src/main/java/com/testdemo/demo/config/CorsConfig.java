package com.testdemo.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 允許所有路徑
                        .allowedOrigins("*") // 允許所有來源，開發用，正式可改成指定來源
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允許的方法
                        .allowedHeaders("*") // 允許的Header
                        .allowCredentials(false) // 是否允許帶cookie，不需要可設false
                        .maxAge(3600); // 預檢請求的有效期
            }
        };
    }
}
