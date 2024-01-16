package com.cos.cercat.mockExamResult.domain;

import com.cos.cercat.certificate.domain.Subject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_result_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mock_exam_result_id")
    @Setter
    private MockExamResult mockExamResult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private UserAnswers userAnswers = new UserAnswers();

    private Integer score;

    private Integer numberOfCorrect;

    private Long totalTakenTime;

    private Integer correctRate;

    public SubjectResult(Subject subject, Integer score, Integer numberOfCorrect, Long totalTakenTime, Integer correctRate, UserAnswers userAnswers) {
        this.subject = subject;
        this.score = score;
        this.numberOfCorrect = numberOfCorrect;
        this.totalTakenTime = totalTakenTime;
        this.correctRate = correctRate;
        setUserAnswers(userAnswers);
    }

    private void setUserAnswers(UserAnswers userAnswers) {
        for (UserAnswer userAnswer : userAnswers.getUserAnswers()) {
            userAnswer.setSubjectResult(this);
        }
        this.userAnswers = userAnswers;
    }

}
