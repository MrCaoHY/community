package com.example.community.controller;

import com.example.community.util.CommunityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @program: community
 * @description:
 * @author: CaoHaiyang
 * @create: 2022-06-14 21:33
 **/
@RestController
public class AlphaController {
    @GetMapping("/cookie/set")
    public String setCookie(HttpServletResponse response) {
        Cookie code = new Cookie("code", CommunityUtil.generateUUID());
        //设置cookie生效范围
        code.setPath("/community");
        //设置cookie生效时间
        code.setMaxAge(600);
        response.addCookie(code);
        return "setCookie()";
    }

    @GetMapping("/cookie/get")
    public String getCookie(@CookieValue("code") String code){
        System.out.println(code);
        return "getCookie";
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping("/session/set")
    public String setSession(HttpSession session) {
       session.setAttribute("id",1);
       session.setAttribute("name","test");
       return "set session";
    }

    @GetMapping("/session/get")
    public String getSession(HttpSession session) {
        System.out.println( session.getAttribute("name"));
        System.out.println( session.getAttribute("id"));
        return "get session";
    }
}
