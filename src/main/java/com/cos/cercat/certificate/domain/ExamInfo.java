package com.cos.cercat.certificate.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ExamInfo {

    @Id
    private Long id;

    @MapsId("id")
    @OneToOne
    @JoinColumn(name = "certificate_id")
    private Certificate certificate;

    @Embedded
    private Subjects subjects = new Subjects();

    @Embedded
    private ExamSchedule examSchedule; //시험 일정

    @Embedded
    private ExamFee examFee; //응시료

    @Embedded
    private ExamTimeLimit examTimeLimit; //제한 시간

    @Embedded
    private PassingCriteria passingCriteria; //시험 방식

    private String description; //자격증 소개

    private String examFormat; //시험 방식

    private String examEligibility; //응시 자격

    private Integer numberOfQuestions; //전체 문항 수

    public ExamInfo(Long id,
                    ExamSchedule examSchedule,
                    ExamFee examFee,
                    ExamTimeLimit examTimeLimit,
                    PassingCriteria passingCriteria,
                    String description, String examFormat,
                    String examEligibility,
                    Integer numberOfQuestions) {
        this.id = id;
        this.examSchedule = examSchedule;
        this.examFee = examFee;
        this.examTimeLimit = examTimeLimit;
        this.passingCriteria = passingCriteria;
        this.description = description;
        this.examFormat = examFormat;
        this.examEligibility = examEligibility;
        this.numberOfQuestions = numberOfQuestions;
    }
}
