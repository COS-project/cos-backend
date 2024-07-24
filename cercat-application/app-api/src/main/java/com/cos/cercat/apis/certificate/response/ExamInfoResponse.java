package com.cos.cercat.apis.certificate.response;



import com.cos.cercat.domain.certificate.*;

public record ExamInfoResponse(
        int examYear, //시험 년도
        int round, //회차
        ExamSchedule examSchedule, //시험 일정
        ExamFee examFee, //응시료
        ExamTimeLimit examTimeLimit, //제한 시간
        PassingCriteria passingCriteria, //시험 방식
        String subjectsInfo,
        String description, //자격증 소개
        String examFormat, //시험 방식
        String examEligibility //응시 자격
) {

    public static ExamInfoResponse from(ExamInformation examInfo) {
        return new ExamInfoResponse(
                examInfo.examYear(),
                examInfo.round(),
                examInfo.examSchedule(),
                examInfo.examFee(),
                examInfo.examTimeLimit(),
                examInfo.passingCriteria(),
                examInfo.subjectsInfo(),
                examInfo.description(),
                examInfo.examFormat(),
                examInfo.examEligibility()
        );
    }
}
