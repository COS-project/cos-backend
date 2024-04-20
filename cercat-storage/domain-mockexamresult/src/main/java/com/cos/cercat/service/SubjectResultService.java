package com.cos.cercat.service;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.repository.SubjectResultRepository;
import com.cos.cercat.dto.SubjectResultsAVG;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectResultService {

    private final SubjectResultRepository subjectResultRepository;

    public List<SubjectResultsAVG> getSubjectResultsAVG(CertificateEntity certificateEntity, UserEntity userEntity, LocalDateTime goalStartDateTime) {
        return subjectResultRepository.getSubjectResultsAVG(certificateEntity.getId(), userEntity.getId(), goalStartDateTime);
    }

}
