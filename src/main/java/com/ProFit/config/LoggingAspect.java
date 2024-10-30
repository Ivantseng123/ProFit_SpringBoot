package com.ProFit.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;

import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final String CSV_FILE_PATH = "logs/aop-logs.csv";

    private final HttpServletRequest request;

    public LoggingAspect(HttpServletRequest request) {
        this.request = request;
    }

    @AfterReturning(pointcut = "execution(* com.ProFit.controller..*(..))", returning = "result")
    public void logAfterRequest(JoinPoint joinPoint, Object result) {
        if (RequestContextHolder.getRequestAttributes() != null) { // 確認 HTTP 請求屬性是否存在
            String timestamp = LocalDateTime.now().toString();
            String methodName = joinPoint.getSignature().getName();
            String response = (result != null) ? result.toString() : "null";

            String requestPath = request.getRequestURI();

            logToCsv(timestamp, methodName, response, requestPath);
        }
    }

    private void logToCsv(String timestamp, String method, String response, String requestPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, true))) {
            writer.write(String.format("%s,%s,%s,%s", timestamp, method, response, requestPath));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
