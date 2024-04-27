package com.cos.cercat.domain;

import com.cos.cercat.certificate.*;
import com.cos.cercat.dto.MockExamSession;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "exam_info")
@AllArgsConstructor
@Builder
public class ExamInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_info_id")
    private Long id;

    private Integer examYear;

    private Integer round;

    private LocalDateTime applicationStartDateTime;

    private LocalDateTime applicationDeadlineDateTime;

    private LocalDateTime resultAnnouncementDateTime;

    private LocalDateTime examDateTime;

    private Integer writtenExamFee;

    private Integer practicalExamFee;

    private Long writtenExamTimeLimit;

    private Long practicalExamTimeLimit;

    private Integer subjectPassingCriteria;

    private Integer totalAvgCriteria;

    private Integer practicalPassingCriteria;

    private String subjectsInfo;

    private String description;

    private String examFormat;

    private String examEligibility;

    public ExamInfoEntity(
            Integer examYear,
            Integer round,
            LocalDateTime applicationStartDateTime,
            LocalDateTime applicationDeadlineDateTime,
            LocalDateTime resultAnnouncementDateTime,
            LocalDateTime examDateTime,
            Integer writtenExamFee,
            Integer practicalExamFee,
            Long writtenExamTimeLimit,
            Long practicalExamTimeLimit,
            Integer subjectPassingCriteria,
            Integer totalAvgCriteria,
            Integer practicalPassingCriteria,
            String subjectsInfo,
            String description,
            String examFormat,
            String examEligibility
    ) {
        this.examYear = examYear;
        this.round = round;
        this.applicationStartDateTime = applicationStartDateTime;
        this.applicationDeadlineDateTime = applicationDeadlineDateTime;
        this.resultAnnouncementDateTime = resultAnnouncementDateTime;
        this.examDateTime = examDateTime;
        this.writtenExamFee = writtenExamFee;
        this.practicalExamFee = practicalExamFee;
        this.writtenExamTimeLimit = writtenExamTimeLimit;
        this.practicalExamTimeLimit = practicalExamTimeLimit;
        this.subjectPassingCriteria = subjectPassingCriteria;
        this.totalAvgCriteria = totalAvgCriteria;
        this.practicalPassingCriteria = practicalPassingCriteria;
        this.subjectsInfo = subjectsInfo;
        this.description = description;
        this.examFormat = examFormat;
        this.examEligibility = examEligibility;
    }

    public static ExamInfoEntity from(ExamInformation examInformation) {
        return ExamInfoEntity.builder()
                .id(examInformation.id())
                .examYear(examInformation.examYear())
                .round(examInformation.round())
                .applicationStartDateTime(examInformation.examSchedule().applicationStartDateTime())
                .applicationDeadlineDateTime(examInformation.examSchedule().applicationDeadlineDateTime())
                .resultAnnouncementDateTime(examInformation.examSchedule().resultAnnouncementDateTime())
                .examDateTime(examInformation.examSchedule().examDateTime())
                .writtenExamFee(examInformation.examFee().writtenExamFee())
                .practicalExamFee(examInformation.examFee().practicalExamFee())
                .writtenExamTimeLimit(examInformation.examTimeLimit().writtenExamTimeLimit())
                .practicalExamTimeLimit(examInformation.examTimeLimit().practicalExamTimeLimit())
                .subjectPassingCriteria(examInformation.passingCriteria().subjectPassingCriteria())
                .totalAvgCriteria(examInformation.passingCriteria().totalAvgCriteria())
                .practicalPassingCriteria(examInformation.passingCriteria().practicalPassingCriteria())
                .subjectsInfo(examInformation.subjectsInfo())
                .description(examInformation.description())
                .examFormat(examInformation.examFormat())
                .examEligibility(examInformation.examEligibility())
                .build();
    }

    public ExamInformation toDomain() {
        return ExamInformation.of(
                id,
                examYear,
                round,
                ExamSchedule.of(
                        applicationStartDateTime,
                        applicationDeadlineDateTime,
                        resultAnnouncementDateTime,
                        examDateTime
                ),
                ExamTimeLimit.of(
                        writtenExamTimeLimit,
                        practicalExamTimeLimit
                ),
                ExamFee.of(
                        writtenExamFee,
                        practicalExamFee
                ),
                PassingCriteria.of(
                        subjectPassingCriteria,
                        totalAvgCriteria,
                        practicalPassingCriteria
                ),
                subjectsInfo,
                description,
                examFormat,
                examEligibility
        );
    }
}