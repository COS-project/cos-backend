package com.cos.cercat.database.post.entity;

import com.cos.cercat.database.certificate.entity.CertificateEntity;
import com.cos.cercat.database.user.entity.UserEntity;
import com.cos.cercat.domain.post.PostType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@OnDelete(action = OnDeleteAction.CASCADE)
@Table(name = "normal_post")
@DiscriminatorValue("NormalPost")
public class NormalPostEntity extends PostEntity {

    public NormalPostEntity(String title,
                            String content,
                            UserEntity userEntity,
                            CertificateEntity certificateEntity,
                            PostType postType) {
        super(title, content, userEntity, certificateEntity, postType);
    }

    public NormalPostEntity(Long id,
                            String title,
                            String content,
                            UserEntity userEntity,
                            CertificateEntity certificateEntity,
                            PostType postType,
                            LocalDateTime createdAt) {
        super(id, title, content, userEntity, certificateEntity, postType,createdAt);
    }

}
