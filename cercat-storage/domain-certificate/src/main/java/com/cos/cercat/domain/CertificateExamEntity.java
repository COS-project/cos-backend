package com.cos.cercat.domain;


import com.cos.cercat.domain.certificate.CertificateExam;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "certificate_exam")
public class CertificateExamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificate_exam_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "certificate_id")
    private CertificateEntity certificateEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "exam_info_id")
    private ExamInfoEntity examInfoEntity;

    @Builder
    public CertificateExamEntity(CertificateEntity certificateEntity, ExamInfoEntity examInfoEntity) {
        this.certificateEntity = certificateEntity;
        this.examInfoEntity = examInfoEntity;
    }

    public CertificateExam toDomain() {
        return new CertificateExam(
                certificateEntity.toDomain(),
                examInfoEntity.toDomain()
        );
    }
}
