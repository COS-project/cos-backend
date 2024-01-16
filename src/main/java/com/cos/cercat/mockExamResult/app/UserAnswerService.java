package com.cos.cercat.mockExamResult.app;

import com.cos.cercat.certificate.domain.Certificate;
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

    public Slice<UserAnswer> getAllUserAnswers(Pageable pageable, User user, Certificate certificate) {
        return userAnswerRepository.getUserAnswersByUserAndCertificate(pageable, user, certificate);
    }

    public Slice<UserAnswer> getUserAnswers(Pageable pageable, MockExamResult mockExamResult) {
        return userAnswerRepository.getUserAnswersByMockExamResult(pageable, mockExamResult);
    }

}
