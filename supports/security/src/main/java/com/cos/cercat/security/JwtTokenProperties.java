package com.cos.cercat.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProperties {

    public static String SECRET_KEY;
    public static int ACCESS_TOKEN_EXPIRATION;
    public static String ACCESS_TOKEN_HEADER;
    public static String REFRESH_TOKEN_HEADER;
    public static String BEARER = "Bearer ";

    @Value("${jwt.secret-key}")
    public void setSecretKey(String secretKey) {
        JwtTokenProperties.SECRET_KEY = secretKey;
    }

    @Value("${jwt.access-token.expiration}")
    public void setAccessTokenExpiration(int accessTokenExpiration) {
        JwtTokenProperties.ACCESS_TOKEN_EXPIRATION = accessTokenExpiration;
    }

    @Value("${jwt.access-token.header}")
    public void setAccessToken(String accessToken) {
        JwtTokenProperties.ACCESS_TOKEN_HEADER = accessToken;
    }

    @Value("${jwt.refresh-token.header}")
    public void setRefreshToken(String refreshToken) {
        JwtTokenProperties.REFRESH_TOKEN_HEADER = refreshToken;
    }
}
