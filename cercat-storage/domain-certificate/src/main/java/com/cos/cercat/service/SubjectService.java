package com.cos.cercat.service;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.SubjectEntity;
import com.cos.cercat.repository.SubjectJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectService {

    private final SubjectJpaRepository subjectJpaRepository;

    public SubjectEntity getSubject(Long subjectId) {

        return subjectJpaRepository.findById(subjectId).orElseThrow(() ->
                new CustomException(ErrorCode.SUBJECT_NOT_FOUND));
    }

    public List<SubjectEntity> getSubjectList(CertificateEntity certificateEntity) {
        return subjectJpaRepository.findSubjectsByCertificateEntity(certificateEntity);
    }

}
