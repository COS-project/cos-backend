package com.cos.cercat.certificate.dto.response;

import com.cos.cercat.certificate.domain.*;

import java.util.List;

public record ExamInfoResponse(
        ExamSchedule examSchedule, //시험 일정
        ExamFee examFee, //응시료
        ExamTimeLimit examTimeLimit, //제한 시간
        PassingCriteria passingCriteria, //시험 방식
        String description, //자격증 소개
        String examFormat, //시험 방식
        String examEligibility, //응시 자격
        Integer numberOfQuestions, //전체 문항 수
        List<SubjectResponse> subjects
) {

    public static ExamInfoResponse from(ExamInfo entity) {
        return new ExamInfoResponse(
                entity.getExamSchedule(),
                entity.getExamFee(),
                entity.getExamTimeLimit(),
                entity.getPassingCriteria(),
                entity.getDescription(),
                entity.getExamFormat(),
                entity.getExamEligibility(),
                entity.getNumberOfQuestions(),
                entity.getSubjects().getAll().stream()
                        .map(SubjectResponse::from)
                        .toList()
        );
    }
}
