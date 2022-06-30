package com.example.community.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @program: community
 * @description: 打印日志
 * @author: CaoHaiyang
 * @create: 2022-06-27 23:21
 **/
@Component
@Slf4j
@Aspect
public class ServiceLogAspect {

    @Pointcut("execution(* com.example.community.service.*.*(..))")
    public void pointcut(){
    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        // 用户IP[1.2.3.4], 在[xxxx], 访问了[com.nowcoder.community.service.xxx()].
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest(); // 先得到request
        String ip = request.getRemoteHost(); // 然后再得到ip地址
        String now = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now());
        String target =
                joinPoint.getSignature().getDeclaringTypeName() // 得到类名
                        + "."
                        + joinPoint.getSignature().getName(); // 得到方法名
        log.info(String.format("用户[%s],在[%s],访问了[%s].", ip, now, target));
    }
}
