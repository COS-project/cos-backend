package com.cos.cercat.domain;

import com.cos.cercat.domain.examreview.ExamDifficulty;
import com.cos.cercat.domain.examreview.ExamReview;
import com.cos.cercat.domain.examreview.ExamReviewContent;
import com.cos.cercat.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "exam_review")
public class ExamReviewEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_review_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "certificate_exam_id")
    private CertificateExamEntity certificateExamEntity;

    @Enumerated(EnumType.STRING)
    private ExamDifficulty examDifficulty;

    private Integer prepareMonths;

    private String content;

    public ExamReview toDomain() {
        return new ExamReview(
                id,
                userEntity.toDomain(),
                new ExamReviewContent(
                        examDifficulty,
                        content
                ),
                prepareMonths,
                createdAt
        );
    }

}
