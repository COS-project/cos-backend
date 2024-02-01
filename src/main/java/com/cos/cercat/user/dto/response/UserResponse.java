package com.cos.cercat.user.dto.response;

import com.cos.cercat.user.domain.User;
import com.cos.cercat.user.dto.UserDTO;

public record UserResponse(
        Long userId,
        String nickname,
        String email,
        String profileImage
    ) {
    public static UserResponse fromEntity(User entity) {
        return fromDTO(UserDTO.fromEntity(entity));
    }

    public static UserResponse fromDTO(UserDTO dto) {
        return new UserResponse(
                dto.getId(),
                dto.getUsername(),
                dto.getEmail(),
                dto.getProfileImage()
        );
    }

}
