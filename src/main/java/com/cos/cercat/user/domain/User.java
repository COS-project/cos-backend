package com.cos.cercat.user.domain;

import com.cos.cercat.global.entity.BaseTimeEntity;
import com.cos.cercat.global.entity.Image;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

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

    private String nickname;

    private String username;

    private String email;

    private String kakaoProfileImage;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image mainProfileImage;

    public void createUserInfo(String nickname, Image profileImage) {
        this.nickname = nickname;
        this.mainProfileImage = profileImage;
    }

    public String getMainProfileImageUrl() {
        if (Objects.nonNull(mainProfileImage)) {
            return mainProfileImage.getImageUrl();
        }
        return "";
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    public User oauthUpdate(String email, String kakaoProfileImageUrl) {
        this.email = email;
        this.kakaoProfileImage = kakaoProfileImageUrl;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }
}
