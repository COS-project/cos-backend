package com.cos.cercat.domain.certificate.domain;

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

    private ExamInfo(
                    ExamSchedule examSchedule,
                    ExamFee examFee,
                    ExamTimeLimit examTimeLimit,
                    PassingCriteria passingCriteria,
                    String subjectsInfo,
                    String description,
                    String examFormat,
                    String examEligibility) {
        this.examSchedule = examSchedule;
        this.examFee = examFee;
        this.examTimeLimit = examTimeLimit;
        this.passingCriteria = passingCriteria;
        this.subjectsInfo = subjectsInfo;
        this.description = description;
        this.examFormat = examFormat;
        this.examEligibility = examEligibility;
    }

    public static ExamInfo of(ExamSchedule examSchedule,
                              ExamFee examFee,
                              ExamTimeLimit examTimeLimit,
                              PassingCriteria passingCriteria,
                              String subjectsInfo,
                              String description,
                              String examFormat,
                              String examEligibility) {
        return new ExamInfo(
                examSchedule,
                examFee,
                examTimeLimit,
                passingCriteria,
                subjectsInfo,
                description,
                examFormat,
                examEligibility
        );
    }

}
