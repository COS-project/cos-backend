package com.cos.cercat.mockExam.domain;

import com.cos.cercat.question.domain.Question;
import com.cos.cercat.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_answer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_result_id")
    @Setter
    private SubjectResult subjectResult;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String selectOption;

    private long takenTime;

    private boolean is_correct;

    public UserAnswer(User user, SubjectResult subjectResult, Question question, String selectOption, long takenTime, boolean is_correct) {
        this.user = user;
        this.subjectResult = subjectResult;
        this.question = question;
        this.selectOption = selectOption;
        this.takenTime = takenTime;
        this.is_correct = is_correct;
    }

    private UserAnswer(User user, Question question, String selectOption, long takenTime, boolean is_correct) {
        this.user = user;
        this.question = question;
        this.selectOption = selectOption;
        this.takenTime = takenTime;
        this.is_correct = is_correct;
    }

    public static UserAnswer of(User user, Question question, String selectOption, long takenTime, boolean is_correct) {
        return new UserAnswer(
                user,
                question,
                selectOption,
                takenTime,
                is_correct
        );
    }
}
