package com.example.community;

import cn.hutool.core.lang.UUID;
import com.example.community.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;

/**
 * @program: community
 * @description:
 * @author: CaoHaiyang
 * @create: 2022-06-07 22:48
 **/
@SpringBootTest
public class MailTest {
    @Autowired
    private MailClient mailClient;

    @Resource
    private TemplateEngine templateEngine;

    @Test
    public void testMail(){
        mailClient.sentMail("caohaiyang666888@outlook.com","test","这是一条测试邮件");
    }

    @Test
    public void  testHtml(){
        Context context = new Context();
        context.setVariable("username","sunday");
        String process = templateEngine.process("/mail/demo", context);
        System.out.println(process);
        mailClient.sentMail("caohaiyang666888@outlook.com","html",process);
    }

    @Test
    public void testUUID(){
        String s = UUID.randomUUID().toString();
        System.out.println(s);
    }
}
