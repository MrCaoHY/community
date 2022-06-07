package com.example.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * @program: community
 * @description:
 * @author: CaoHaiyang
 * @create: 2022-05-29 20:32
 **/
@Configuration
public class AlphaConfig {
    @Bean
    public SimpleDateFormat simpleDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

}
