package com.cos.cercat.domain;

import com.cos.cercat.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExamReview extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_review_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "exam_info_id")
    private CertificateExamEntity certificateExamEntity;

    @Enumerated(EnumType.STRING)
    private ExamDifficulty examDifficulty;

    private Integer prepareMonths;

    private String content;

    public ExamReview(UserEntity userEntity, CertificateExamEntity certificateExamEntity, ExamDifficulty examDifficulty, Integer prepareMonths, String content) {
        this.userEntity = userEntity;
        this.certificateExamEntity = certificateExamEntity;
        this.examDifficulty = examDifficulty;
        this.prepareMonths = prepareMonths;
        this.content = content;
    }
}
