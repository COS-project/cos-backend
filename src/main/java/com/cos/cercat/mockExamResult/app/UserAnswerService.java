package com.cos.cercat.mockExamResult.app;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.global.exception.CustomException;
import com.cos.cercat.global.exception.ErrorCode;
import com.cos.cercat.mockExamResult.domain.MockExamResult;
import com.cos.cercat.mockExamResult.domain.UserAnswer;
import com.cos.cercat.mockExamResult.repository.UserAnswerRepository;
import com.cos.cercat.user.domain.User;
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

    public Slice<UserAnswer> getAllIncorrectUserAnswers(Pageable pageable, User user, Certificate certificate) {
        return userAnswerRepository.getIncorrectUserAnswersByUserAndCertificate(pageable, user.getId(), certificate.getId());
    }

    public Slice<UserAnswer> getIncorrectUserAnswers(Pageable pageable, MockExamResult mockExamResult) {
        return userAnswerRepository.getIncorrectUserAnswersByMockExamResult(pageable, mockExamResult.getId());
    }

    public UserAnswer getUserAnswer(Long userAnswerId) {
        return userAnswerRepository.findById(userAnswerId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_ANSWER_NOT_FOUND));
    }


}
