package com.cos.cercat.Member.domain.entity;

import com.cos.cercat.Member.domain.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Member oauthUpdate(String email, String profileImageUrl) {
        this.email = email;
        this.profileImage = profileImageUrl;
        return this;
    }
}
