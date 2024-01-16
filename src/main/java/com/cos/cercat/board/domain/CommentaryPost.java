package com.cos.cercat.board.domain;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.question.domain.Question;
import com.cos.cercat.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CommentaryPost extends Post {

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private CommentaryPost(String title, String content, User user, Certificate certificate, Question question) {
        super(title, content, user, certificate);
        this.question = question;
    }

    public static CommentaryPost of(String title, String content, User user, Certificate certificate, Question question) {
        return new CommentaryPost(
                title,
                content,
                user,
                certificate,
                question);
    }

    public void updateQuestion(Question question) {
        this.question = question;
    }
}
