package com.cos.cercat.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi post() {
        return GroupedOpenApi.builder()
                .group("게시글")
                .packagesToScan("com.cos.cercat.board.api")
                .build();
    }

    @Bean
    public GroupedOpenApi alarm() {
        return GroupedOpenApi.builder()
                .group("알림")
                .packagesToScan("com.cos.cercat.alarm.api")
                .build();
    }

    @Bean
    public GroupedOpenApi certificate() {
        return GroupedOpenApi.builder()
                .group("자격증")
                .packagesToScan("com.cos.cercat.certificate.api")
                .build();
    }

    @Bean
    public GroupedOpenApi comment() {
        return GroupedOpenApi.builder()
                .group("댓글")
                .packagesToScan("com.cos.cercat.comment.api")
                .build();
    }

    @Bean
    public GroupedOpenApi learning() {
        return GroupedOpenApi.builder()
                .group("학습")
                .packagesToScan("com.cos.cercat.learning.api")
                .build();
    }

    @Bean
    public GroupedOpenApi like() {
        return GroupedOpenApi.builder()
                .group("좋아요")
                .packagesToScan("com.cos.cercat.like.api")
                .build();
    }

    @Bean
    public GroupedOpenApi mockExam() {
        return GroupedOpenApi.builder()
                .group("모의고사")
                .packagesToScan("com.cos.cercat.mockExam.api")
                .build();
    }

    @Bean
    public GroupedOpenApi user() {
        return GroupedOpenApi.builder()
                .group("유저")
                .packagesToScan("com.cos.cercat.user.api")
                .build();
    }

    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .version("1.0.0")
                .title("Cercat API")
                .description("Cercat API 문서입니다.");

        // SecuritySecheme명
        String accessTokenSchemeName = "Access-Token";
        String refreshTokenSchemeName = "Refresh-Token";
        // API 요청헤더에 인증정보 포함
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(accessTokenSchemeName)
                .addList(refreshTokenSchemeName);
        // SecuritySchemes 등록
        Components components = new Components()
                .addSecuritySchemes(accessTokenSchemeName, new SecurityScheme()
                        .name(accessTokenSchemeName)
                        .type(SecurityScheme.Type.APIKEY) // HTTP 방식
                        .scheme("bearer")
                        .in(SecurityScheme.In.HEADER)
                        .bearerFormat("JWT"))
                .addSecuritySchemes(refreshTokenSchemeName, new SecurityScheme()
                        .name(refreshTokenSchemeName)
                        .type(SecurityScheme.Type.APIKEY) // HTTP 방식
                        .scheme("bearer")
                        .in(SecurityScheme.In.HEADER)
                        .bearerFormat("JWT"));// 토큰 형식을 지정하는 임의의 문자(Optional)

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }

}
