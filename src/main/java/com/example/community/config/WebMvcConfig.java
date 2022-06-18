package com.example.community.config;

import com.example.community.controller.interpretor.AlphaInterceptor;
import com.example.community.controller.interpretor.LoginRequiredInterceptor;
import com.example.community.controller.interpretor.LoginTicketInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: community
 * @description:
 * @author: CaoHaiyang
 * @create: 2022-06-15 23:19
 **/

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
//    @Autowired
//    private AlphaInterceptor alphaInterceptor;

    @Autowired
    private LoginTicketInterceptor loginTicketInterceptor;

    @Autowired
    private LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(alphaInterceptor).excludePathPatterns("/*/*.css", "/*/*.js", "/*/*.png", "/*/*.jpg", "/*/*.jpeg")
//                .addPathPatterns("/register", "/login");
        registry.addInterceptor(loginTicketInterceptor).excludePathPatterns("/*/*.css", "/*/*.js", "/*/*.png", "/*/*.jpg", "/*/*.jpeg");

        registry.addInterceptor(loginRequiredInterceptor).excludePathPatterns("/*/*.css", "/*/*.js", "/*/*.png", "/*/*.jpg", "/*/*.jpeg");

    }
}
