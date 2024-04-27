package com.cos.cercat.domain;

import com.cos.cercat.mockexamresult.SubjectResult;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subject_result")
public class SubjectResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_result_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mock_exam_result_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MockExamResultEntity mockExamResultEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private SubjectEntity subjectEntity;

    private Integer score;

    private Integer numberOfCorrect;

    private Long totalTakenTime;

    private Integer correctRate;

    @Builder
    public SubjectResultEntity(MockExamResultEntity mockExamResultEntity,
                               SubjectEntity subjectEntity,
                               Integer score,
                               Integer numberOfCorrect,
                               Long totalTakenTime,
                               Integer correctRate) {
        this.mockExamResultEntity = mockExamResultEntity;
        this.subjectEntity = subjectEntity;
        this.score = score;
        this.numberOfCorrect = numberOfCorrect;
        this.totalTakenTime = totalTakenTime;
        this.correctRate = correctRate;
    }

    public SubjectResult toDomain() {
        return new SubjectResult(
                subjectEntity.toDomain(),
                score,
                numberOfCorrect,
                totalTakenTime,
                correctRate
        );
    }
}
