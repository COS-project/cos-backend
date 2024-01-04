package com.cos.cercat.mockExam.api.response;

import com.cos.cercat.mockExam.app.dto.MockExamDTO;
import com.cos.cercat.mockExam.app.dto.MockExamResultDTO;
import com.cos.cercat.mockExam.app.dto.SubjectDTO;
import com.cos.cercat.mockExam.app.dto.SubjectResultDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

public record MockExamWithResultResponse(
        Integer round,
        Boolean isTake,
        @JsonInclude(Include.NON_NULL)
        Integer totalScore,
        @JsonInclude(Include.NON_NULL)
        Integer score

        ) {

    public static MockExamWithResultResponse from(MockExamResultDTO dto) {
        return new MockExamWithResultResponse(
                dto.mockExamDTO().round(),
                true,
                dto.subjectResultDTOList().stream()
                        .map(SubjectResultDTO::subjectDTO)
                        .map(SubjectDTO::totalScore)
                        .reduce(0, Integer::sum),
                dto.subjectResultDTOList().stream()
                        .map(SubjectResultDTO::score)
                        .reduce(0, Integer::sum)
        );
    }

    public static MockExamWithResultResponse from(MockExamDTO mockExamDTO) {
        return new MockExamWithResultResponse(
                mockExamDTO.round(),
                false,
                null,
                null
        );
    }
}
