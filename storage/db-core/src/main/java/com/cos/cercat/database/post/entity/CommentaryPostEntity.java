package com.cos.cercat.database.post.entity;

import com.cos.cercat.database.certificate.entity.CertificateEntity;
import com.cos.cercat.database.mockexam.entity.QuestionEntity;
import com.cos.cercat.database.user.entity.UserEntity;
import com.cos.cercat.domain.post.PostType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@OnDelete(action = OnDeleteAction.CASCADE)
@Table(name = "commentary_post")
@DiscriminatorValue("CommentaryPost")
public class CommentaryPostEntity extends PostEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @OnDelete(action =  CASCADE)
    private QuestionEntity questionEntity;

    public CommentaryPostEntity(String title,
                                String content,
                                UserEntity userEntity,
                                CertificateEntity certificateEntity,
                                PostType postType,
                                QuestionEntity questionEntity) {
        super(title, content, userEntity, certificateEntity, postType);
        this.questionEntity = questionEntity;
    }

    public CommentaryPostEntity(Long id,
                                String title,
                                String content,
                                UserEntity userEntity,
                                CertificateEntity certificateEntity,
                                PostType postType,
                                Integer likeCount,
                                QuestionEntity questionEntity,
                                LocalDateTime createdAt) {
        super(id, title, content, userEntity, certificateEntity, postType, likeCount, createdAt);
        this.questionEntity = questionEntity;
    }
}
