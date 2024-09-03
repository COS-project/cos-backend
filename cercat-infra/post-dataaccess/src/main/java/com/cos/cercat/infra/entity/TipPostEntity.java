package com.cos.cercat.infra.entity;

import com.cos.cercat.domain.post.PostType;
import com.cos.cercat.domain.post.TipPost;
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
@Table(name = "tip_post")
@DiscriminatorValue("TipPost")
public class TipPostEntity extends PostEntity {

    public TipPostEntity(String title, String content, UserEntity userEntity, CertificateEntity certificateEntity, PostType postType) {
        super(title, content, userEntity, certificateEntity, postType);
    }

    public TipPostEntity(Long id,
                         String title,
                         String content,
                         UserEntity userEntity,
                         CertificateEntity certificateEntity,
                         PostType postType,
                         Integer likeCount,
                         LocalDateTime createdAt) {
        super(id, title, content, userEntity, certificateEntity, postType, likeCount,createdAt);
    }

    public static TipPostEntity from(TipPost tipPost) {

        return new TipPostEntity(
                tipPost.getId(),
                tipPost.getPostContent().getTitle(),
                tipPost.getPostContent().getContent(),
                UserEntity.from(tipPost.getUser()),
                CertificateEntity.from(tipPost.getCertificate()),
                tipPost.getPostStatus().getPostType(),
                tipPost.getPostStatus().getLikeCount(),
                tipPost.getDateTime().createdAt());
    }
}
