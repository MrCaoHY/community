package com.example.community.util;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @program: community
 * @description: 取出cookie中对应属性值
 * @author: CaoHaiyang
 * @create: 2022-06-15 23:01
 **/
public class CookieUtil {
    public static String getValue(HttpServletRequest resquest, String name) {
        if (resquest == null || name == null) {
            throw new IllegalArgumentException("参数为空");
        }
        Cookie[] cookies = resquest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
