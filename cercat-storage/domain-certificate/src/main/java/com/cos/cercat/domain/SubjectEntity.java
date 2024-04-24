package com.cos.cercat.domain;

import com.cos.cercat.domain.certificate.Subject;
import com.cos.cercat.domain.certificate.SubjectInfo;
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

    public static SubjectEntity from(Subject subject) {

        if (subject == null) {
            return null;
        }

        return new SubjectEntity(
                subject.subjectId(),
                CertificateEntity.from(subject.certificate()),
                subject.subjectInfo().subjectName(),
                subject.subjectInfo().numberOfQuestions(),
                subject.subjectInfo().totalScore()
        );
    }

    public static SubjectEntity from(SubjectInfo subjectInfo) {
        return SubjectEntity.builder()
                .subjectName(subjectInfo.subjectName())
                .numberOfQuestions(subjectInfo.numberOfQuestions())
                .totalScore(subjectInfo.totalScore())
                .build();
    }

    public Subject toDomain() {
        return new Subject(
                id,
                certificateEntity.toDomain(),
                new SubjectInfo(subjectName, numberOfQuestions, totalScore)
        );
    }




}
