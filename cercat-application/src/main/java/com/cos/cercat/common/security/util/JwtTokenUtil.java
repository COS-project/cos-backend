package com.cos.cercat.common.security.util;

import com.cos.cercat.exception.CustomException;
import com.cos.cercat.exception.ErrorCode;
import com.cos.cercat.user.service.JwtTokenService;
import com.nimbusds.jose.util.Pair;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Calendar;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenUtil implements JwtTokenService {

    @Value("${jwt.access-token.header}")
    private String accessHeader;

    @Value("${jwt.refresh-token.header}")
    private String refreshHeader;

    /**
     * JWT의 Subject와 Claim으로 email 사용 -> 클레임의 name을 "email"으로 설정
     * JWT의 헤더에 들어오는 값 : 'Authorization(Key) = Bearer {토큰} (Value)' 형식
     */
    private static final String EMAIL_CLAIM = "email";
    private static final String BEARER = "Bearer ";

    private final JwtTokenizer jwtTokenizer;

    /**
     * AccessToken + RefreshToken 헤더에 실어서 보내기
     */
    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(accessHeader, BEARER + accessToken);
        response.setHeader(refreshHeader, BEARER + refreshToken);
        log.info("Access Token, Refresh Token 헤더 설정 완료");
    }

    public long getAccessTokenExpirationMillis(String accessToken) {

        String replaced = accessToken.replace(BEARER, "");
        return extractAllClaims(replaced).getExpiration().getTime() - Calendar.getInstance().getTimeInMillis();
    }

    /**
     * 헤더에서 RefreshToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    /**
     * 헤더에서 AccessToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     */
    public Optional<String> extractAccessToken(HttpServletRequest request) {

        return Optional.ofNullable(request.getHeader("Access-Token"))
                .filter(accessToken -> accessToken.startsWith(BEARER))
                .map(accessToken -> accessToken.replace(BEARER, ""));

    }

    /**
     * AccessToken에서 Email 추출
     * 추출 전에 JWT.require()로 검증기 생성
     * verify로 AceessToken 검증 후
     * 유효하다면 getClaim()으로 이메일 추출
     * 유효하지 않다면 빈 Optional 객체 반환
     */

    public String extractEmailFromRefreshToken(String refreshToken) {
        try {
            Key key = jwtTokenizer.getKeyFromBase64EncodedKey();

            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            log.warn("리프레시 토큰으로 부터 이메일 추출중 예외 발생 - {}", e.getMessage());
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
    }

    public Pair<String, String> extractEmail(String token) {

        try {
            return Pair.of(token, extractAllClaims(token).get(EMAIL_CLAIM, String.class));
        } catch (ExpiredJwtException e) {
            log.warn("액세스 토큰 만료 - {}", e.getMessage());
            throw new CustomException(ErrorCode.ACCESS_TOKEN_EXPIRED);
        } catch (Exception e) {
            log.warn("엑세스 토큰으로 부터 이메일 추출중 예외 발생 - {}", e.getMessage());
            throw new CustomException(ErrorCode.INVALID_ACCESS_TOKEN, e.getMessage());
        }
    }

    public Claims extractAllClaims(String token) {

        Key key = jwtTokenizer.getKeyFromBase64EncodedKey();

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

    }


}

