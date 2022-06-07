package com.example.community;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @program: community
 * @description:
 * @author: CaoHaiyang
 * @create: 2022-06-01 22:16
 **/
@SpringBootTest
@Slf4j
public class LoggerTest {

    @Test
    public void test(){
        System.out.println(log.getName());
        log.info("这是info");
        log.debug("这是debug");
        log.error("这是error");
        log.trace("这是trace");
        log.warn("这是warn");
    }
}
