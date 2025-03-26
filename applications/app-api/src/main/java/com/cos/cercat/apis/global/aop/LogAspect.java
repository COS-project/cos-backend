package com.cos.cercat.apis.global.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Slf4j
@Component
public class LogAspect {

    @Pointcut("execution(* com.cos.cercat.apis..*Api.*(..)) && !execution(* com.cos.cercat.apis.HealthCheckApi.*(..))")
    public void controller() {
    }

    @Around("controller()")
    public Object loggingController(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String methodName = joinPoint.getSignature().getName();
        Map<String, Object> params = new HashMap<>();

        long start = System.currentTimeMillis();

        try {
            String decodedURI = URLDecoder.decode(request.getRequestURI(), "UTF-8");
            String clientIp = getClientIp(request);

            params.put("method", methodName);
            params.put("params", getParams(request));
            params.put("request_uri", decodedURI);
            params.put("http_method", request.getMethod());
            params.put("client_ip", clientIp);
        } catch (Exception e) {
            log.error("LoggerAspect error", e);
        }

        log.info("[{}] {} | IP: {} | METHOD: {} | PARAMS: {}",
                params.get("http_method"),
                params.get("request_uri"),
                params.get("client_ip"),
                params.get("method"),
                params.get("params"));

        try {
            Object result = joinPoint.proceed();
            return result;
        } finally {
            long end = System.currentTimeMillis();
            long timeInMs = end - start;
            log.info("Completed [{}] {} | TIME: {}ms", params.get("http_method"), params.get("request_uri"), timeInMs);
        }
    }

    private static JSONObject getParams(HttpServletRequest request) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            String replaceParam = param.replaceAll("\\.", "-");
            jsonObject.put(replaceParam, request.getParameter(param));
        }
        return jsonObject;
    }

    private String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("Proxy-Client-IP");
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("HTTP_CLIENT_IP");
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }
}
