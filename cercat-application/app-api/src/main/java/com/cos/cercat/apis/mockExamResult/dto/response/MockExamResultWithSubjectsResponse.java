package com.cos.cercat.apis.mockExamResult.dto.response;

import com.cos.cercat.apis.mockExam.response.MockExamResponse;
import com.cos.cercat.domain.MockExamResultEntity;

import java.util.List;

public record MockExamResultWithSubjectsResponse(
        Long mockExamResultId,
        Integer round,
        MockExamResponse mockExam,
        List<SubjectResultResponse> subjectResults,
        Integer totalScore
) {

//    public static MockExamResultWithSubjectsResponse from(MockExamResultEntity entity) {
//        return new MockExamResultWithSubjectsResponse(
//                entity.getId(),
//                entity.getRound(),
//                MockExamResponse.from(entity.getMockExamEntity()),
//                entity.getSubjectResults().getSubjectResultEntities().stream()
//                        .map(SubjectResultResponse::from)
//                        .toList(),
//                entity.getTotalScore()
//        );
//    }
}
