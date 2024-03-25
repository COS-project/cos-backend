package com.cos.cercat.user.service;

public interface JwtTokenService {

    long getAccessTokenExpirationMillis(String accessToken);
}
