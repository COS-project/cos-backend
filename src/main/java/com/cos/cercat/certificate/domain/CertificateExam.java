package com.cos.cercat.certificate.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CertificateExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificate_exam_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "certificate_id")
    private Certificate certificate;

    private Integer examYear;

    private Integer round;

    private CertificateExam(Certificate certificate, Integer examYear, Integer round) {
        this.certificate = certificate;
        this.examYear = examYear;
        this.round = round;
    }

    public static CertificateExam of(Certificate certificate, Integer examYear, Integer round) {
        return new CertificateExam(
                certificate,
                examYear,
                round
        );
    }
}
