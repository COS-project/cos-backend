package com.cos.cercat.user.dto.response;

import com.cos.cercat.user.domain.User;

public record UserResponse(
        Long userId,
        String username,
        String email,
        String profileImage
    ) {
    public static UserResponse from(User entity) {
        return new UserResponse(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getProfileImage()
        );
    }
}
