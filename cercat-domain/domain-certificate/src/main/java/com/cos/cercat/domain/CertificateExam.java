package com.cos.cercat.domain;


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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "exam_info_id")
    private ExamInfo examInfo;

    private Integer examYear;

    private Integer round;

    private CertificateExam(Certificate certificate, ExamInfo examInfo, Integer examYear, Integer round) {
        this.certificate = certificate;
        this.examInfo = examInfo;
        this.examYear = examYear;
        this.round = round;
    }

    public static CertificateExam of(Certificate certificate, ExamInfo examInfo, Integer examYear, Integer round) {
        return new CertificateExam(
                certificate,
                examInfo,
                examYear,
                round
        );
    }
}
