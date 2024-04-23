package com.cos.cercat.domain;

import com.cos.cercat.common.domain.Image;
import com.cos.cercat.domain.user.Role;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserProfileImage;
import com.cos.cercat.entity.ImageEntity;
import com.cos.cercat.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Builder
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET removed_at = NOW() WHERE user_id = ?")
public class UserEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String nickname;

    private String username;

    private String email;

    private String kakaoProfileImage;

    private LocalDateTime removedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private ImageEntity mainProfileImageEntity;

    public void createUserInfo(String nickname, ImageEntity profileImageEntity) {
        this.nickname = nickname;
        this.mainProfileImageEntity = profileImageEntity;
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    public UserEntity oauthUpdate(String email, String kakaoProfileImageUrl) {
        this.email = email;
        this.kakaoProfileImage = kakaoProfileImageUrl;
        return this;
    }

    public Image getMainProfileImage() {
        return mainProfileImageEntity != null ?
                mainProfileImageEntity.toImage()
                : Image.notAssigned();
    }

    public User toDomain() {
        return new User(
                this.id,
                this.nickname,
                this.email,
                this.username,
                UserProfileImage.of(
                        mainProfileImageEntity != null ?
                                mainProfileImageEntity.toImage()
                                : Image.notAssigned(),
                        kakaoProfileImage
                ),
                this.role
        );
    }

    public static UserEntity from(User user) {
        Image mainProfileImage = user.userProfileImage().getMainProfileImage();
        return UserEntity.builder()
                .id(user.id())
                .nickname(user.nickname())
                .email(user.email())
                .username(user.username())
                .kakaoProfileImage(user.userProfileImage().getKakaoProfileImageUrl())
                .mainProfileImageEntity(
                        mainProfileImage != null ?
                        ImageEntity.from(mainProfileImage)
                        : null)
                .role(user.userRole())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }
}
