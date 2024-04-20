package com.cos.cercat.domain.post;

import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.entity.Image;
import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.Question;
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
public class CommentaryPostEntity extends PostEntity {

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public CommentaryPostEntity(String title, String content, UserEntity userEntity, CertificateEntity certificateEntity, PostType postType, List<Image> images, Question question) {
        super(title, content, userEntity, certificateEntity, postType, images);
        this.question = question;
    }

    public void updateQuestion(Question question) {
        this.question = question;
    }
}
