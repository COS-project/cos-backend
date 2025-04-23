package com.cos.cercat.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // allowedOriginPatterns("*") // 와일드카드 대신 특정 도메인 명시
                .allowedOrigins(
                        "http://localhost:3000", // 개발 환경 등 프론트엔드 출처 예시
                        "https://cercat.o-r.kr", // 본인 애플리케이션 도메인 (필요시)
                        "https://appleid.apple.com", // Apple OAuth 출처
                        "https://kauth.kakao.com",
                        "https://cercat.vercel.app"
                )
                .allowedMethods("DELETE", "GET", "POST", "PATCH", "PUT", "OPTIONS") // OPTIONS 메소드 추가 (CORS preflight 요청에 필요)
                .allowedHeaders("Access-Control-Allow-Headers","Access-Control-Allow-Origin","Access-Control-Request-Method", "Access-Control-Request-Headers","Origin","Cache-Control", "Content-Type", "Authorization", "Access-Token", "Refresh-Token")
                .exposedHeaders("*")
                .allowCredentials(true) // 자격 증명 (쿠키 포함) 허용
                .maxAge(3600);
    }
}
