package com.cos.cercat.mockExamResult.domain;

import com.cos.cercat.global.entity.BaseTimeEntity;
import com.cos.cercat.mockExam.domain.Question;
import com.cos.cercat.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAnswer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_answer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_result_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SubjectResult subjectResult;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private int selectOptionSeq;

    private long takenTime;

    private boolean isCorrect;

    private boolean isReviewed;

    @Builder
    public UserAnswer(User user, Question question, int selectOptionSeq, long takenTime, boolean isCorrect, boolean isReviewed) {
        this.user = user;
        this.question = question;
        this.selectOptionSeq = selectOptionSeq;
        this.takenTime = takenTime;
        this.isCorrect = isCorrect;
        this.isReviewed = isReviewed;
    }

    public void review() {
        this.isReviewed = true;
    }

    public boolean isAuthorized(User user) {
        return this.user.equals(user);
    }
}
