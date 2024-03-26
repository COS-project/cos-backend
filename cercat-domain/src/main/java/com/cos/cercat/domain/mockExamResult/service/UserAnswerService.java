package com.cos.cercat.domain.mockExamResult.service;

import com.cos.cercat.domain.certificate.domain.Certificate;
import com.cos.cercat.domain.mockExamResult.repository.UserAnswerRepository;
import com.cos.cercat.domain.user.domain.User;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.mockExamResult.domain.MockExamResult;
import com.cos.cercat.domain.mockExamResult.domain.UserAnswer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAnswerService {

    private final UserAnswerRepository userAnswerRepository;

    public Slice<UserAnswer> getAllWrongUserAnswers(Pageable pageable, User user, Certificate certificate) {
        return userAnswerRepository.getWrongUserAnswersByUserAndCertificate(pageable, user.getId(), certificate.getId());
    }

    public Slice<UserAnswer> getWrongUserAnswers(Pageable pageable, MockExamResult mockExamResult) {
        return userAnswerRepository.getWrongUserAnswersByMockExamResult(pageable, mockExamResult.getId());
    }

    public UserAnswer getUserAnswer(Long userAnswerId) {
        return userAnswerRepository.findById(userAnswerId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_ANSWER_NOT_FOUND));
    }


}
