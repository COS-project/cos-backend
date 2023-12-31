package com.cos.cercat.mockExam.api.response;

import com.cos.cercat.mockExam.domain.entity.MockExam;
import com.cos.cercat.mockExam.domain.entity.MockExamResult;
import com.cos.cercat.mockExam.domain.entity.Subject;
import com.cos.cercat.mockExam.domain.entity.SubjectResult;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

public record MockExamWithResultResponse(
        Integer round,
        Boolean isTake,
        @JsonInclude(Include.NON_NULL)
        Integer totalScore,
        @JsonInclude(Include.NON_NULL)
        Integer score
) {

    public static MockExamWithResultResponse from(MockExam mockExam, List<MockExamResult> mockExamResults) {

        boolean isTake = !mockExamResults.isEmpty();

        return new MockExamWithResultResponse(
                mockExam.getRound(),
                isTake,
                (isTake) ? mockExamResults.get(0).getSubjectResults().stream()
                        .map(SubjectResult::getSubject)
                        .map(Subject::getTotalScore)
                        .reduce(0, Integer::sum) : null,
                (isTake) ? mockExamResults.get(0).getSubjectResults().stream()
                        .map(SubjectResult::getScore)
                        .reduce(0, Integer::sum) : null
        );
    }
}
