package com.cos.cercat.mockExam.app.service;

import com.cos.cercat.Member.domain.entity.Member;
import com.cos.cercat.mockExam.app.dto.MockExamResultDTO;
import com.cos.cercat.mockExam.domain.entity.MockExam;
import com.cos.cercat.mockExam.domain.repository.MockExamResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MockExamResultService {

    private final MockExamResultRepository mockExamResultRepository;

    public List<MockExamResultDTO> getMockExamResults(MockExam mockExam, Member member) {

        return mockExamResultRepository.findMockExamResultByMockExamAndMember(mockExam, member).stream()
                .map(MockExamResultDTO::from)
                .sorted(Comparator.comparing(MockExamResultDTO::examDate).reversed())
                .toList();
    }



}
