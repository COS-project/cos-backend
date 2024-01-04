package com.cos.cercat.mockExam.app.dto;


import com.cos.cercat.Member.app.dto.MemberDTO;
import com.cos.cercat.mockExam.domain.entity.MockExamResult;

import java.time.LocalDateTime;
import java.util.List;

public record MockExamResultDTO(
        Long mockExamResultId,
        MockExamDTO mockExamDTO,
        Integer round,
        MemberDTO memberDTO,
        List<SubjectResultDTO> subjectResultDTOList,
        LocalDateTime examDate
) {

    public static MockExamResultDTO from(MockExamResult entity) {
        return new MockExamResultDTO(
                entity.getId(),
                MockExamDTO.from(entity.getMockExam()),
                entity.getRound(),
                MemberDTO.from(entity.getMember()),
                entity.getSubjectResults().stream()
                        .map(SubjectResultDTO::from)
                        .toList(),
                entity.getCreatedAt()
        );
    }

}
