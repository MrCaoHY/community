package com.example.community.controller.advice;

import com.example.community.util.CommunityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @program: community
 * @description:
 * @author: CaoHaiyang
 * @create: 2022-06-27 15:28
 **/
@Slf4j
@ControllerAdvice(annotations = Controller.class)
public class ExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.error("服务器发生异常:{}",e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
            log.error(element.toString());
        }
        String xRequestedWith = request.getHeader("x-requested-with");
        String type = "XMLHttpRequest"; // 如果 xRequestedWith 为 XMLHttpRequest 则为 Ajax 异步HTTP请求
        if (type.equals(xRequestedWith)) {
            // text/plain的意思是将文件设置为纯文本的形式，浏览器在获取到这种文件时并不会对其进行处理
            response.setContentType("application/plain;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(CommunityUtil.getJSONString(1, "服务器异常！"));
        } else {
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }

}
