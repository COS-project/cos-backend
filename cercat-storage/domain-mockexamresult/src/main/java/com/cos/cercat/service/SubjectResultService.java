package com.cos.cercat.service;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.repository.SubjectResultJpaRepository;
import com.cos.cercat.dto.SubjectResultsAVG;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectResultService {

    private final SubjectResultJpaRepository subjectResultJpaRepository;

    public List<SubjectResultsAVG> getSubjectResultsAVG(CertificateEntity certificateEntity, UserEntity userEntity, LocalDateTime goalStartDateTime) {
        return subjectResultJpaRepository.getSubjectResultsAVG(certificateEntity.getId(), userEntity.getId(), goalStartDateTime);
    }

}
