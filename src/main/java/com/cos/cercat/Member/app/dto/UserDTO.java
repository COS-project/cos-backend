package com.cos.cercat.Member.app.dto;

import com.cos.cercat.Member.domain.Role;
import com.cos.cercat.Member.domain.entity.User;

public record UserDTO(
        Long memberId,
        String username,
        String email,
        String profileImage,
        Role role
) {
    public static UserDTO from(User entity) {
        return new UserDTO(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getProfileImage(),
                entity.getRole()
        );
    }
}
