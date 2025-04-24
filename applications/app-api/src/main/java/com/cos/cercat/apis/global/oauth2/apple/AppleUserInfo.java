package com.cos.cercat.apis.global.oauth2.apple;

public record AppleUserInfo(
        AppleUserName name,
        String email
) {
    public String getFullName() {
        return name.lastName() + name.firstName();
    }
}
