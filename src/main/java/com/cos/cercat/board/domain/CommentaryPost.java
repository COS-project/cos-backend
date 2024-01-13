package com.cos.cercat.board.domain;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.mockExam.domain.Question;
import com.cos.cercat.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CommentaryPost extends Post {

    @OneToOne
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
}
