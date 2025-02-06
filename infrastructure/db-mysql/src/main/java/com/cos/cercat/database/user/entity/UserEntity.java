package com.cos.cercat.database.user.entity;

import com.cos.cercat.domain.common.Image;
import com.cos.cercat.database.common.entity.BaseTimeEntity;
import com.cos.cercat.database.common.entity.ImageEntity;
import com.cos.cercat.domain.user.UserInfo;
import com.cos.cercat.domain.user.Role;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserProfileImage;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(name = "users")
@SuperBuilder
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

    @Enumerated(EnumType.STRING)
    private Role role;

    public UserEntity(Long id,
                      String nickname,
                      String username,
                      String email,
                      String kakaoProfileImage,
                      LocalDateTime removedAt,
                      ImageEntity mainProfileImageEntity,
                      Role role,
                      LocalDateTime createdAt) {
        this.id = id;
        this.nickname = nickname;
        this.username = username;
        this.email = email;
        this.kakaoProfileImage = kakaoProfileImage;
        this.removedAt = removedAt;
        this.mainProfileImageEntity = mainProfileImageEntity;
        this.role = role;
        this.createdAt = createdAt;
    }

    public static UserEntity from(UserInfo userInfo) {
        return UserEntity.builder()
                .username(userInfo.username())
                .email(userInfo.email())
                .kakaoProfileImage(userInfo.kakaoProfileImage())
                .role(Role.ROLE_GUEST)
                .build();
    }

    public Image getMainProfileImage() {
        return mainProfileImageEntity != null ?
                mainProfileImageEntity.toImage()
                : null;
    }

    public User toDomain() {
        return new User(
                this.id,
                nickname != null ?
                        nickname :
                        username,
                this.email,
                this.username,
                UserProfileImage.of(
                        getMainProfileImage(),
                        kakaoProfileImage
                ),
                this.role,
                this.createdAt
        );
    }

    public static UserEntity from(User user) {
        Image mainProfileImage = user.getUserProfileImage().getMainProfileImage();
        return UserEntity.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .username(user.getUsername())
                .kakaoProfileImage(user.getUserProfileImage().getKakaoProfileImageUrl())
                .mainProfileImageEntity(
                        mainProfileImage != null ?
                        ImageEntity.from(mainProfileImage)
                        : null)
                .role(user.getUserRole())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
