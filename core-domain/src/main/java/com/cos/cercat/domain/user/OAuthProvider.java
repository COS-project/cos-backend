package com.cos.cercat.domain.user;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OAuthProvider {
    KAKAO("kakao"),
    APPLE("apple");

    private final String provider;

    public static OAuthProvider fromString(String provider) {
        for (OAuthProvider oAuthProvider : OAuthProvider.values()) {
            if (oAuthProvider.provider.equalsIgnoreCase(provider)) {
                return oAuthProvider;
            }
        }
        throw new IllegalArgumentException("Unknown provider: " + provider);
    }
}
