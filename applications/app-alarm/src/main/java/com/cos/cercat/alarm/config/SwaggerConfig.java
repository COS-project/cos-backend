package com.cos.cercat.alarm.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SwaggerConfig {

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
                        .bearerFormat("JWT"));

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }

}
