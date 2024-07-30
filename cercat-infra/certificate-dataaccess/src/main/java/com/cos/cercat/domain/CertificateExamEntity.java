package com.cos.cercat.domain;


import com.cos.cercat.domain.certificate.CertificateExam;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;

import static org.hibernate.annotations.OnDeleteAction.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "certificate_exam")
public class CertificateExamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificate_exam_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "certificate_id")
    @OnDelete(action = CASCADE)
    private CertificateEntity certificateEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "exam_info_id")
    private ExamInfoEntity examInfoEntity;

    public static CertificateExamEntity from(CertificateExam certificateExam) {
        return CertificateExamEntity.builder()
                .id(certificateExam.id())
                .certificateEntity(CertificateEntity.from(certificateExam.certificate()))
                .examInfoEntity(ExamInfoEntity.from(certificateExam.examInformation()))
                .build();

    }

    public CertificateExam toDomain() {
        return new CertificateExam(
                id,
                certificateEntity.toDomain(),
                examInfoEntity.toDomain()
        );
    }
}
