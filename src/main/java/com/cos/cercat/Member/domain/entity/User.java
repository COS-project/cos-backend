package com.cos.cercat.Member.domain.entity;

import com.cos.cercat.Member.domain.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class User {

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
}
