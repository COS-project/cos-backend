package com.cos.cercat.domain;

import com.cos.cercat.domain.certificate.Subject;
import com.cos.cercat.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subject")
public class SubjectEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_id")
    @Setter
    private CertificateEntity certificateEntity;

    private String subjectName;

    private Integer numberOfQuestions;

    private Integer totalScore;

    @Builder
    public SubjectEntity(String subjectName, Integer numberOfQuestions, Integer totalScore) {
        this.subjectName = subjectName;
        this.numberOfQuestions = numberOfQuestions;
        this.totalScore = totalScore;
    }

    public Subject toSubject() {
        return new Subject(
                this.id,
                this.certificateEntity.getId(),
                this.subjectName,
                this.numberOfQuestions,
                this.totalScore
        );
    }
}
