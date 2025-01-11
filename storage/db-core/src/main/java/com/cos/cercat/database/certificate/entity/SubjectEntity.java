package com.cos.cercat.database.certificate.entity;

import com.cos.cercat.database.common.entity.BaseTimeEntity;
import com.cos.cercat.domain.certificate.Subject;
import com.cos.cercat.domain.certificate.SubjectInfo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;

import static org.hibernate.annotations.OnDeleteAction.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "subject")
public class SubjectEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_id")
    @Setter
    @OnDelete(action = CASCADE)
    private CertificateEntity certificateEntity;

    private Integer subjectSequence;

    private String subjectName;

    private Integer numberOfQuestions;

    private Integer totalScore;

    public static SubjectEntity from(Subject subject) {

        if (subject == null) {
            return null;
        }

        return new SubjectEntity(
                subject.subjectId(),
                CertificateEntity.from(subject.certificate()),
                subject.subjectInfo().subjectSequence(),
                subject.subjectInfo().subjectName(),
                subject.subjectInfo().numberOfQuestions(),
                subject.subjectInfo().totalScore()
        );
    }

    public static SubjectEntity from(SubjectInfo subjectInfo) {
        return SubjectEntity.builder()
                .subjectSequence(subjectInfo.subjectSequence())
                .subjectName(subjectInfo.subjectName())
                .numberOfQuestions(subjectInfo.numberOfQuestions())
                .totalScore(subjectInfo.totalScore())
                .build();
    }

    public static SubjectEntity from(Long subjectId) {
        return SubjectEntity.builder()
                .id(subjectId)
                .build();
    }

    public Subject toDomain() {
        return new Subject(
                id,
                certificateEntity.toDomain(),
                new SubjectInfo(
                        subjectSequence,
                        subjectName,
                        numberOfQuestions,
                        totalScore
                )
        );
    }




}
