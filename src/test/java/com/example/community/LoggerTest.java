package com.example.community;

import com.example.community.util.CommunityUtil;
import com.example.community.util.SensitiveFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void test(){
        System.out.println(log.getName());
        log.info("这是info");
        log.debug("这是debug");
        log.error("这是error");
        log.trace("这是trace");
        log.warn("这是warn");
    }

    @Test
    public void testMd5(){
        System.out.println(CommunityUtil.md5("123456"+"7216475a4e634036ab44d2fe94cef4c3"));
    }
    @Test
    public void testFilter(){
        String s = "打架斗殴吸毒嫖娼";
        String filter = sensitiveFilter.filter(s);
        System.out.println(filter);
    }
}
