package com.cos.cercat.apis.mockExam.app.usecase;

import com.cos.cercat.apis.mockExam.dto.response.ExamYearWithRoundsResponse;
import com.cos.cercat.apis.mockExam.dto.response.MockExamWithResultResponse;
import com.cos.cercat.apis.mockExam.dto.response.QuestionListResponse;
import com.cos.cercat.apis.mockExam.dto.response.QuestionResponse;
import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.service.CertificateService;
import com.cos.cercat.domain.Certificate;
import com.cos.cercat.service.MockExamService;
import com.cos.cercat.service.QuestionService;
import com.cos.cercat.domain.MockExam;
import com.cos.cercat.service.MockExamResultService;
import com.cos.cercat.domain.MockExamResult;
import com.cos.cercat.service.UserService;
import com.cos.cercat.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@UseCase
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MockExamFetchUseCase {

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
    public List<MockExamWithResultResponse> getMockExamList(Long certificateId, Integer examYear, Long userId) {
        List<MockExamWithResultResponse> resultResponses = new ArrayList<>();
        Certificate certificate = certificateService.getCertificate(certificateId);

        List<MockExam> mockExamList = mockExamService.getMockExamList(certificate, examYear);
        User user = userService.getUser(userId);

        for (MockExam mockExam : mockExamList) {
            List<MockExamResult> userMockExamResults = mockExamResultService.getMockExamResults(mockExam, user);
            resultResponses.add(MockExamWithResultResponse.from(mockExam, userMockExamResults));
        }
        resultResponses.sort(Comparator.comparing(MockExamWithResultResponse::round));

        log.info("user - {}, certificate - {}, {}년도 모의고사 리스트 조회", user.getEmail(), certificate.getCertificateName(), examYear);
        return resultResponses;
    }

    /**
     * 모의고사 ID를 통해 특정 모의고사의 모든 문제들을 가져온다.
     *
     * @param mockExamId 모의고사 고유 ID
     * */
    @Cacheable(cacheNames = "QUESTIONS", key = "#mockExamId")
    public QuestionListResponse getQuestionList(Long mockExamId) {
        MockExam mockExam = mockExamService.getMockExam(mockExamId);

        log.info("{}년도 {}회차 모의고사 문제리스트 조회", mockExam.getExamYear(), mockExam.getRound());
        return QuestionListResponse.from(questionService.getQuestionListByMockExam(mockExam).stream()
                .map(QuestionResponse::from)
                .toList());
    }


    /***
     * 자격증의 모의고사의 시험년도와 해당년도의 회차정보를 조회합니다.
     * @param certificateId 자격증 ID
     * @return 시험년도가 Key이고 List형태의 회차정보를 Value로 가지는 Map을 반환합니다.
     */
    public ExamYearWithRoundsResponse getMockExamInfoList(Long certificateId) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        List<Integer> mockExamYears = mockExamService.getMockExamYears(certificate);

        log.info("certificate - {} 자격증의 모의고사 시험년도 및 회차정보 조회", certificate.getCertificateName());
        return ExamYearWithRoundsResponse.from(
                mockExamYears.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        mockExamYear -> mockExamService.getMockExamRounds(certificate, mockExamYear)
                )));
    }

}
