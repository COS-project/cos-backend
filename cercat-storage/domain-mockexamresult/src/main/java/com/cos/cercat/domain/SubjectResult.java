package com.cos.cercat.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_result_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mock_exam_result_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MockExamResult mockExamResult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private SubjectEntity subjectEntity;

    private UserAnswers userAnswers = new UserAnswers();

    private Integer score;

    private Integer numberOfCorrect;

    private Long totalTakenTime;

    private Integer correctRate;

    @Builder
    public SubjectResult(SubjectEntity subjectEntity, Integer score, Integer numberOfCorrect, Long totalTakenTime, Integer correctRate, UserAnswers userAnswers) {
        this.subjectEntity = subjectEntity;
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
