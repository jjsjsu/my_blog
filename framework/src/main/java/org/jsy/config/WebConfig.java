package org.jsy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//允许跨域的路径
                .allowedHeaders("*")//允许的header属性
                .allowCredentials(true)//允许使用缓存
                .allowedOriginPatterns("*")//允许跨域访问的路径
                .allowedMethods("GET","POST","DELETE","PUT")//允许访问的方法
                .maxAge(3600);//设置最大的跨域时间
    }
}
