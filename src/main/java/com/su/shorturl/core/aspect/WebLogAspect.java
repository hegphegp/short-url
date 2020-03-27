package com.su.shorturl.core.aspect;


import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 请求和返回日志 切面
 */
@Aspect
@Component
public class WebLogAspect {
    private Logger log = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(* com.su.shorturl.modules.*.controller..*.*(..))")//两个..代表所有子目录，最后括号里的两个..代表所有参数
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        log.debug("请求地址:{}\n IP:{}\n 参数:{}\n ", request.getRequestURL().toString(), ServletUtil.getClientIP(request), Arrays.toString(joinPoint.getArgs()));
//        log.info("请求地址 : " + request.getRequestURL().toString());
//        log.info("HTTP METHOD : " + request.getMethod());
//        log.info("IP : " + ServletUtil.getClientIP(request)); //request.getRemoteAddr()
//        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
//                + joinPoint.getSignature().getName());
//        log.info("参数 : " + Arrays.toString(joinPoint.getArgs()));

    }

    @AfterReturning(returning = "ret", pointcut = "logPointCut()")// returning的值和doAfterReturning的参数名一致
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.debug("返回值:{}", JSONUtil.toJsonStr(ret));
    }

    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object ob = pjp.proceed();// ob 为方法的返回值
        log.info("耗时:{}", (System.currentTimeMillis() - startTime));
        return ob;
    }

}
