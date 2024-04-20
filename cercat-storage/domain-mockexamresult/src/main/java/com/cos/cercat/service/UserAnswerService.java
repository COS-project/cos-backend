package com.cos.cercat.service;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.repository.UserAnswerRepository;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.MockExamResult;
import com.cos.cercat.domain.UserAnswer;
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

    public Slice<UserAnswer> getAllWrongUserAnswers(Pageable pageable, UserEntity userEntity, CertificateEntity certificateEntity) {
        return userAnswerRepository.getWrongUserAnswersByUserEntityAndCertificateEntity(pageable, userEntity.getId(), certificateEntity.getId());
    }

    public Slice<UserAnswer> getWrongUserAnswers(Pageable pageable, MockExamResult mockExamResult) {
        return userAnswerRepository.getWrongUserAnswersByMockExamResult(pageable, mockExamResult.getId());
    }

    public UserAnswer getUserAnswer(Long userAnswerId) {
        return userAnswerRepository.findById(userAnswerId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_ANSWER_NOT_FOUND));
    }


}
