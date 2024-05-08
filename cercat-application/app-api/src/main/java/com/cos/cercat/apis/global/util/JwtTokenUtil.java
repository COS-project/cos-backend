package com.cos.cercat.apis.global.util;

import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.user.TargetUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Calendar;
import java.util.Optional;

import static com.cos.cercat.apis.global.util.JwtTokenizer.getKeyFromBase64EncodedKey;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenUtil {

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

    /**
     * AccessToken + RefreshToken 헤더에 실어서 보내기
     */
    public void setTokenInHeader(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(accessHeader, BEARER + accessToken);
        response.setHeader(refreshHeader, BEARER + refreshToken);
        log.info("Access Token, Refresh Token 헤더 설정 완료");
    }

    public static long getAccessTokenExpirationMillis(String accessToken) {

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

    public TargetUser extractTargetUser(String token) {
        try {
            Key key = getKeyFromBase64EncodedKey();

            String subject = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return TargetUser.from(Long.valueOf(subject));
        } catch (ExpiredJwtException e) {
            log.warn("토큰으로 부터 정보 추출중 예외 발생 - {}", e.getMessage());
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    public static Claims extractAllClaims(String token) {

        Key key = getKeyFromBase64EncodedKey();

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

    }


}

