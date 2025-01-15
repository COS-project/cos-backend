package com.cos.cercat.apis.global.security;

import com.cos.cercat.apis.global.oauth2.CustomOAuth2UserService;
import com.cos.cercat.apis.global.oauth2.OAuth2LoginFailureHandler;
import com.cos.cercat.apis.global.oauth2.OAuth2MemberSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApiSecurityConfig {

    private final OAuth2MemberSuccessHandler oAuth2MemberSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final HttpCookieAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository;

    @Bean
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .oauth2Login(
                        oauth2 -> oauth2
                                .loginPage("/oauth2/authorization/kakao")
                                .authorizationEndpoint(
                                        authorizationEndpointConfig -> authorizationEndpointConfig
                                                .authorizationRequestRepository(
                                                        cookieOAuth2AuthorizationRequestRepository)
                                )
                                .successHandler(oAuth2MemberSuccessHandler)
                                .failureHandler(oAuth2LoginFailureHandler)
                                .userInfoEndpoint(
                                        userInfoEndpointConfig -> userInfoEndpointConfig
                                                .userService(customOAuth2UserService)
                                )

                );

        return http.build();
    }
}
