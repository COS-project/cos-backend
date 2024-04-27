package com.cos.cercat.domain;

import com.cos.cercat.common.domain.Image;
import com.cos.cercat.entity.ImageEntity;
import com.cos.cercat.entity.BaseTimeEntity;
import com.cos.cercat.user.NewUser;
import com.cos.cercat.user.Role;
import com.cos.cercat.user.User;
import com.cos.cercat.user.UserProfileImage;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

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


    @Enumerated(EnumType.STRING)
    private Role role;

    public static UserEntity from(NewUser newUser) {
        return UserEntity.builder()
                .username(newUser.username())
                .email(newUser.email())
                .kakaoProfileImage(newUser.kakaoProfileImage())
                .role(newUser.role())
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
                this.role
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
                .build();
    }
}
