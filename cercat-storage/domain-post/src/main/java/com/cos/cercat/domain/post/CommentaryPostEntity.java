package com.cos.cercat.domain.post;

import com.cos.cercat.domain.QuestionEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.entity.Image;
import com.cos.cercat.domain.CertificateEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@OnDelete(action = OnDeleteAction.CASCADE)
@Table(name = "commentary_post")
public class CommentaryPostEntity extends PostEntity {

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity questionEntity;

    public CommentaryPostEntity(String title,
                                String content,
                                UserEntity userEntity,
                                CertificateEntity certificateEntity,
                                PostType postType,
                                List<Image> images,
                                QuestionEntity questionEntity) {
        super(title, content, userEntity, certificateEntity, postType, images);
        this.questionEntity = questionEntity;
    }

    public CommentaryPostEntity(String title,
                                String content,
                                UserEntity userEntity,
                                CertificateEntity certificateEntity,
                                PostType postType,
                                QuestionEntity questionEntity) {
        super(title, content, userEntity, certificateEntity, postType);
        this.questionEntity = questionEntity;
    }

    public void updateQuestion(QuestionEntity questionEntity) {
        this.questionEntity = questionEntity;
    }
}
