package com.cos.cercat.user.domain;

import com.cos.cercat.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Builder
@Table(name = "Users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User oauthUpdate(String email, String profileImageUrl) {
        this.email = email;
        this.profileImage = profileImageUrl;
        return this;
    }

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public User(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }
}
