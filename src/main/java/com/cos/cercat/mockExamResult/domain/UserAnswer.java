package com.cos.cercat.mockExamResult.domain;

import com.cos.cercat.global.entity.BaseTimeEntity;
import com.cos.cercat.mockExam.domain.Question;
import com.cos.cercat.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    private String selectOption;

    private Long takenTime;

    private Boolean is_correct;

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
