package com.cos.cercat.domain.user;

public record Provider(
        OAuthProvider provider,
        String providerId
) {
    public static Provider of(String provider, String providerId) {
        return new Provider(OAuthProvider.fromString(provider), providerId);
    }
}
