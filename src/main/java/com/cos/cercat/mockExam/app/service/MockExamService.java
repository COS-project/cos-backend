package com.cos.cercat.mockExam.app.service;

import com.cos.cercat.Member.domain.entity.Member;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.mockExam.api.response.MockExamWithResultResponse;
import com.cos.cercat.mockExam.app.dto.MockExamDTO;
import com.cos.cercat.mockExam.app.dto.MockExamResultDTO;
import com.cos.cercat.mockExam.domain.entity.MockExam;
import com.cos.cercat.mockExam.domain.repository.MockExamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MockExamService {

    private final MockExamRepository mockExamRepository;
    private final MockExamResultService mockExamResultService;

    @Transactional
    public List<MockExamWithResultResponse> getMockExamList(Long certificateId, Integer examYear, Member member) {
        List<MockExamWithResultResponse> resultResponses = new ArrayList<>();
        List<MockExam> mockExamList = mockExamRepository.findMockExamByCertificate_IdAndExamYear(certificateId, examYear);

        for (MockExam mockExam : mockExamList) {
            List<MockExamResultDTO> mockExamResults = mockExamResultService.getMockExamResults(mockExam, member);
            MockExamWithResultResponse responseToAdd;
            if (mockExamResults.isEmpty()) {
                responseToAdd = MockExamWithResultResponse.from(MockExamDTO.from(mockExam)); // 비어있다면 미응시
            } else {
                responseToAdd = MockExamWithResultResponse.from(mockExamResults.get(0)); // 가장 최근 결과
            }
            resultResponses.add(responseToAdd);
        }
        resultResponses.sort(Comparator.comparing(MockExamWithResultResponse::round));
        return resultResponses;
    }

    public MockExam getMockExamById(Long mockExamId) {
        return mockExamRepository.findById(mockExamId).orElseThrow(() ->
                new CustomException(ErrorCode.MOCK_EXAM_NOT_FOUND));
    }


}
