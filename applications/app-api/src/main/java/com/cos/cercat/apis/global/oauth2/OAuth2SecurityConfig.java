package com.cos.cercat.apis.global.oauth2;

import com.cos.cercat.apis.global.oauth2.apple.AppleTokenProperties;
import com.cos.cercat.apis.global.oauth2.apple.CustomRequestEntityConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class OAuth2SecurityConfig {

    private final OAuth2MemberSuccessHandler oAuth2MemberSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final HttpCookieAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository;
    private final CustomRequestEntityConverter customRequestEntityConverter;

    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
        DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
        accessTokenResponseClient.setRequestEntityConverter(customRequestEntityConverter);
        return accessTokenResponseClient;
    }

    @Bean
    public SecurityFilterChain oAuth2SecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/oauth2/**", "/login/**")
                .oauth2Login(
                        oauth2 -> oauth2
                                .tokenEndpoint(tokenEndpointConfig ->
                                        tokenEndpointConfig.accessTokenResponseClient(accessTokenResponseClient()))
                                .authorizationEndpoint(
                                        authorizationEndpointConfig -> authorizationEndpointConfig
                                                .baseUri("/oauth2/authorization")
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
