package com.cos.cercat.apis.global.security.config;

import com.cos.cercat.apis.global.security.filter.JwtExceptionHandleFilter;
import com.cos.cercat.apis.global.security.filter.JwtTokenFilter;
import com.cos.cercat.apis.global.security.handler.JwtAuthenticationEntryPoint;
import com.cos.cercat.apis.global.security.handler.OAuth2LoginFailureHandler;
import com.cos.cercat.apis.global.security.handler.OAuth2MemberSuccessHandler;
import com.cos.cercat.apis.global.security.handler.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2MemberSuccessHandler oAuth2MemberSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtTokenFilter jwtTokenFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private static final String[] SWAGGER_URIS = {
            /* swagger v2 */
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/","/health", "/api/v2/alarms/subscribe", "/error", "/error/**", "/css/**", "/images/**", "/js/**", "/favicon.ico", "/h2-console/**").permitAll()
                                .requestMatchers(SWAGGER_URIS).permitAll()
                                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                                .anyRequest().authenticated())
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .oauth2Login(
                        oauth2 -> oauth2
                                .loginPage("/oauth2/authorization/kakao")
                                .successHandler(oAuth2MemberSuccessHandler)
                                .failureHandler(oAuth2LoginFailureHandler)
                                .userInfoEndpoint(
                                        userInfoEndpointConfig -> userInfoEndpointConfig
                                                .userService(customOAuth2UserService)
                                )
                );

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JwtExceptionHandleFilter(), JwtTokenFilter.class);
        return http.build();

    }


}
