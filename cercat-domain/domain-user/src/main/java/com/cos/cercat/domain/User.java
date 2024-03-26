package com.cos.cercat.domain;

import com.cos.cercat.entity.Image;
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
@Table(name = "Users")
@SQLDelete(sql = "UPDATE users SET removed_at = NOW() WHERE user_id = ?")
public class User extends BaseTimeEntity {

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

    public void updateRole() {
        this.role = Role.ROLE_USER;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }
}
