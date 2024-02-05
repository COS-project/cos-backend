package com.cos.cercat.mockExam.app;

import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.mockExam.domain.MockExam;
import com.cos.cercat.mockExam.dto.response.ExamYearWithRoundsResponse;
import com.cos.cercat.mockExam.dto.response.QuestionListResponse;
import com.cos.cercat.mockExamResult.app.MockExamResultService;
import com.cos.cercat.mockExamResult.domain.MockExamResult;
import com.cos.cercat.mockExam.dto.response.MockExamWithResultResponse;
import com.cos.cercat.mockExam.dto.response.QuestionResponse;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MockExamFetchService {

    private final MockExamResultService mockExamResultService;
    private final MockExamService mockExamService;
    private final QuestionService questionService;
    private final UserService userService;
    private final CertificateService certificateService;

    /**
     * 자격증 ID와 시험년도, 유저정보를 통해 특정 자격증의 시험년도에 해당하는 모의고사들을 가져온다.
     * 유저가 모의고사를 푼 이력이있다면 모의고사 점수도 같이 반환한다.
     *
     * @param certificateId 자격증 고유 ID
     * @param examYear 시험년도
     * @param userId 로그인한 유저
     * */
    @Transactional(readOnly = true)
    public List<MockExamWithResultResponse> getMockExamList(Long certificateId, Integer examYear, Long userId) {
        List<MockExamWithResultResponse> resultResponses = new ArrayList<>();

        List<MockExam> mockExamList = mockExamService.getMockExamList(certificateService.getCertificate(certificateId), examYear);
        User user = userService.getUser(userId);

        for (MockExam mockExam : mockExamList) {
            List<MockExamResult> userMockExamResults = mockExamResultService.getMockExamResults(mockExam, user);
            resultResponses.add(MockExamWithResultResponse.from(mockExam, userMockExamResults));
        }
        resultResponses.sort(Comparator.comparing(MockExamWithResultResponse::round));

        return resultResponses;
    }

    /**
     * 모의고사 ID를 통해 특정 모의고사의 모든 문제들을 가져온다.
     *
     * @param mockExamId 모의고사 고유 ID
     * */
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "questions", key = "'all'")
    public QuestionListResponse getQuestionList(Long mockExamId) {

        MockExam mockExam = mockExamService.getMockExam(mockExamId);

        return QuestionListResponse.from(questionService.getQuestionListByMockExam(mockExam).stream()
                .map(QuestionResponse::from)
                .toList());
    }


    public ExamYearWithRoundsResponse getMockExamInfoList(Long certificateId) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        List<Integer> mockExamYears = mockExamService.getMockExamYears(certificate);

        return ExamYearWithRoundsResponse.from(
                mockExamYears.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        mockExamYear -> mockExamService.getMockExamRounds(certificate, mockExamYear)
                )));
    }

}
