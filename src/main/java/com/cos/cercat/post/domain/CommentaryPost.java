package com.cos.cercat.post.domain;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.global.common.Image;
import com.cos.cercat.mockExam.domain.Question;
import com.cos.cercat.user.domain.User;
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
public class CommentaryPost extends Post {

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public CommentaryPost(String title, String content, User user, Certificate certificate, PostType postType, List<Image> images, Question question) {
        super(title, content, user, certificate, postType, images);
        this.question = question;
    }

    public void updateQuestion(Question question) {
        this.question = question;
    }
}
