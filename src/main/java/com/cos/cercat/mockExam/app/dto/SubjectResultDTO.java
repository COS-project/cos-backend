package com.cos.cercat.mockExam.app.dto;

import com.cos.cercat.mockExam.domain.entity.SubjectResult;

public record SubjectResultDTO(
        Long subjectResultId,
        SubjectDTO subjectDTO,
        int score
) {
    public static SubjectResultDTO from(SubjectResult entity) {
        return new SubjectResultDTO(
                entity.getId(),
                SubjectDTO.from(entity.getSubject()),
                entity.getScore()
        );
    }
}
