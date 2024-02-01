package com.cos.cercat.global.util;

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
import java.util.Map;

@Getter
@Component
public class JwtTokenizer {

    @Value("${jwt.token.secret-key}")
    private String secretKey;

    @Getter
    @Value("${jwt.access-token.expire-length}")
    private int accessTokenExpirationMinutes;

    private String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Map<String, Object> claims,
                                      String subject,
                                      Date expiration) {

        Key key = getKeyFromBase64EncodedKey();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String email) {

        Key key = getKeyFromBase64EncodedKey();

        return Jwts.builder()
                .setSubject(email)
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

    public Key getKeyFromBase64EncodedKey() {
        String base64SecretKey = encodeBase64SecretKey(secretKey);
        byte[] keyBytes = Decoders.BASE64.decode(base64SecretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return key;
    }
}
