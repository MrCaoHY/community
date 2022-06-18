package com.example.community.controller.interpretor;

import com.example.community.annotation.LoginRequired;
import com.example.community.util.HostHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @program: community
 * @description:
 * @author: CaoHaiyang
 * @create: 2022-06-18 15:49
 **/
@Slf4j
@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {


    @Autowired
    private HostHolder hostHolder;

    /**
     * 在Controller之前执行
     * 在请求之前将user暂存到hostHolder
     * 如果已登录, hostHolder 有 对象
     * @param request
     * @param response
     * @param handler 只判断拦截的是否是 方法
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            //获取拦截到的method对象
            Method method = handlerMethod.getMethod();
            LoginRequired annotation = method.getAnnotation(LoginRequired.class);
            if (annotation != null && hostHolder.getUser()==null) {
//                request.getContextPath()
                response.sendRedirect(request.getContextPath()+"/login");
                log.info("------------没有登录访问其他页面被拦截------------");
                return false;
            }
        }
        return true;
    }
}
