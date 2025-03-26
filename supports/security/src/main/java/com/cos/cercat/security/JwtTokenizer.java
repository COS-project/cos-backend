package com.cos.cercat.security;

import static com.cos.cercat.security.JwtTokenProperties.*;
import com.cos.cercat.security.exception.InvalidTokenException;
import com.cos.cercat.domain.user.UserId;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


@Getter
@Component
public class JwtTokenizer {

    public static String generateAccessToken(UserId userId) {

        Key key = getKeyFromSecretKey(SECRET_KEY);

        return Jwts.builder()
                .setSubject(userId.userId().toString())
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(getTokenExpiration())
                .signWith(key)
                .compact();
    }

    public static String generateRefreshToken(UserId userId) {

        Key key = getKeyFromSecretKey(SECRET_KEY);

        return Jwts.builder()
                .setSubject(userId.userId().toString())
                .setIssuedAt(Calendar.getInstance().getTime())
                .signWith(key)
                .compact();
    }

    public static Date getTokenExpiration() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, ACCESS_TOKEN_EXPIRATION);
        return calendar.getTime();
    }

    public static Key getKeyFromSecretKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static void setInHeader(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setHeader(ACCESS_TOKEN_HEADER, BEARER + accessToken);
        response.setHeader(REFRESH_TOKEN_HEADER, BEARER + refreshToken);
    }

    public static Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(ACCESS_TOKEN_HEADER))
                .filter(accessToken -> accessToken.startsWith(BEARER))
                .map(accessToken -> accessToken.replace(BEARER, ""));
    }

    public static Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(REFRESH_TOKEN_HEADER))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    public static UserId extractTargetUser(String token) {
        try {
            Key key = getKeyFromSecretKey(SECRET_KEY);

            String subject = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return UserId.from(Long.valueOf(subject));
        } catch (ExpiredJwtException e) {
            throw InvalidTokenException.EXCEPTION;
        }
    }
}
