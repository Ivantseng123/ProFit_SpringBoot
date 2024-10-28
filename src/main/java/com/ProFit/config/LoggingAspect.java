package com.ProFit.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // 紀錄請求前的信息
    @Before("execution(* com.ProFit.controller..*(..)) && @annotation(requestMapping)")
    public void logBeforeRequest(JoinPoint joinPoint, RequestMapping requestMapping) {
        String methodName = joinPoint.getSignature().getName();
        String arguments = Arrays.toString(joinPoint.getArgs());
        String url = Arrays.toString(requestMapping.value());
        
        logger.info("請求的 URL: {} - 方法: {} - 參數: {}", url, methodName, arguments);
    }

    // 紀錄請求完成後的返回結果
    @AfterReturning(pointcut = "execution(* com.ProFit.controller..*(..))", returning = "result")
    public void logAfterRequest(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        
        logger.info("完成的方法: {} - 回應 : {}", methodName, result);
    }
}

