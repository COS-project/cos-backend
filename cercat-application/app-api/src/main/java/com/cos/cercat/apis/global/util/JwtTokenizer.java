package com.cos.cercat.apis.global.util;

import com.cos.cercat.user.TargetUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;

@Getter
@Component
public class JwtTokenizer {

    private static String secretKey;

    private static int accessTokenExpirationMinutes;

    @Value("${jwt.token.secret-key}")
    public void setSecretKey(String secretKey) {
        JwtTokenizer.secretKey = secretKey;
    }

    @Value("${jwt.access-token.expire-length}")
    public void setAccessTokenExpirationMinutes(int accessTokenExpirationMinutes) {
        JwtTokenizer.accessTokenExpirationMinutes = accessTokenExpirationMinutes;
    }

    public String generateAccessToken(TargetUser targetUser,
                                      Date expiration) {

        Key key = getKeyFromBase64EncodedKey();

        return Jwts.builder()
                .setSubject(targetUser.userId().toString())
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(TargetUser targetUser) {

        Key key = getKeyFromBase64EncodedKey();

        return Jwts.builder()
                .setSubject(targetUser.userId().toString())
                .setIssuedAt(Calendar.getInstance().getTime())
                .signWith(key)
                .compact();
    }

    public Jws<Claims> getClaims(String jws) {
        Key key = getKeyFromBase64EncodedKey();

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
    }

    public void verifySignature(String jws) {
        Key key = getKeyFromBase64EncodedKey();

        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
    }

    public Date getTokenExpiration() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, accessTokenExpirationMinutes);

        return calendar.getTime();
    }

    public static Key getKeyFromBase64EncodedKey() {
        String base64SecretKey = encodeBase64SecretKey(secretKey);
        byte[] keyBytes = Decoders.BASE64.decode(base64SecretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return key;
    }

    private static String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
