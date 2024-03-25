package com.cos.cercat.common.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver resolver;

    public JwtAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException){

        if (request.getAttribute("com/cos/cercat/exception") == null) { //null인 경우는 리프레시 토큰을 재발급후에 필터 태운뒤에 리턴하는 경우밖에없음
            resolver.resolveException(request, response, null, authException);
            return;
        }

        resolver.resolveException(request, response, null, (Exception) request.getAttribute("com/cos/cercat/exception"));
    }
}
