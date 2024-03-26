package com.cos.cercat.domain.user.service;

public interface JwtTokenService {

    long getAccessTokenExpirationMillis(String accessToken);
}
