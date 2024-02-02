package com.cos.cercat.certificate.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ExamInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_info_id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "certificate_id")
    private CertificateExam certificateExam;

    @Embedded
    private ExamSchedule examSchedule; //시험 일정

    @Embedded
    private ExamFee examFee; //응시료

    @Embedded
    private ExamTimeLimit examTimeLimit; //제한 시간

    @Embedded
    private PassingCriteria passingCriteria; //시험 방식

    private String subjectsInfo;

    private String description; //자격증 소개

    private String examFormat; //시험 방식

    private String examEligibility; //응시 자격

    public ExamInfo(
                    CertificateExam certificateExam,
                    ExamSchedule examSchedule,
                    ExamFee examFee,
                    ExamTimeLimit examTimeLimit,
                    PassingCriteria passingCriteria,
                    String subjectsInfo,
                    String description,
                    String examFormat,
                    String examEligibility) {
        this.certificateExam = certificateExam;
        this.examSchedule = examSchedule;
        this.examFee = examFee;
        this.examTimeLimit = examTimeLimit;
        this.passingCriteria = passingCriteria;
        this.subjectsInfo = subjectsInfo;
        this.description = description;
        this.examFormat = examFormat;
        this.examEligibility = examEligibility;
    }
}
