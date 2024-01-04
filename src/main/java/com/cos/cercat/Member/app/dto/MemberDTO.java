package com.cos.cercat.Member.app.dto;

import com.cos.cercat.Member.domain.Role;
import com.cos.cercat.Member.domain.entity.Member;

public record MemberDTO(
        Long memberId,
        String username,
        String email,
        String profileImage,
        Role role
) {
    public static MemberDTO from(Member entity) {
        return new MemberDTO(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getProfileImage(),
                entity.getRole()
        );
    }
}
