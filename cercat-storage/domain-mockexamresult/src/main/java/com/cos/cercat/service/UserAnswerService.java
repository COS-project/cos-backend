package com.cos.cercat.service;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.MockExamResultEntity;
import com.cos.cercat.domain.UserAnswerEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.repository.UserAnswerJpaRepository;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAnswerService {

    private final UserAnswerJpaRepository userAnswerJpaRepository;

    public Slice<UserAnswerEntity> getAllWrongUserAnswers(Pageable pageable, UserEntity userEntity, CertificateEntity certificateEntity) {
        return userAnswerJpaRepository.getWrongUserAnswersByUserEntityAndCertificateEntity(pageable, userEntity.getId(), certificateEntity.getId());
    }

    public Slice<UserAnswerEntity> getWrongUserAnswers(Pageable pageable, MockExamResultEntity mockExamResultEntity) {
        return userAnswerJpaRepository.getWrongUserAnswersByMockExamResult(pageable, mockExamResultEntity.getId());
    }

    public UserAnswerEntity getUserAnswer(Long userAnswerId) {
        return userAnswerJpaRepository.findById(userAnswerId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_ANSWER_NOT_FOUND));
    }


}
