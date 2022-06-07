package com.example.community.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @program: community
 * @description:
 * @author: CaoHaiyang
 * @create: 2022-06-07 22:26
 **/
@Slf4j
@Component
public class MailClient {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sentMail(String to,String subject, String content){
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content,true);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
           log.info("发送邮件失败" + e.getMessage());
        }
    }
}
