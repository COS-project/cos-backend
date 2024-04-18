package com.cos.cercat.repository;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.SubjectEntity;
import com.cos.cercat.domain.certificate.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CertificateCoreRepository implements CertificateRepository {

    private final CertificateJpaRepository certificateJpaRepository;
    private final SubjectJpaRepository subjectJpaRepository;


    @Override
    public List<Certificate> findAll() {
        return certificateJpaRepository.findAll().stream()
                .map(this::convertToCertificate)
                .toList();
    }

    @Override
    public void append(NewCertificate newCertificate) {

        List<NewSubject> newSubjects = newCertificate.getSubjectList();
        List<SubjectEntity> subjectEntities = newSubjects.stream()
                .map(subject -> SubjectEntity.builder()
                        .subjectName(subject.subjectName())
                        .totalScore(subject.totalScore())
                        .numberOfQuestions(subject.numberOfQuestions())
                        .build())
                .toList();

        CertificateEntity certificateEntity = CertificateEntity.of(newCertificate.certificateName(), subjectEntities);

        certificateJpaRepository.save(certificateEntity);
    }

    private Certificate convertToCertificate(CertificateEntity certificateEntity) {
        List<SubjectEntity> subjectEntities = subjectJpaRepository.findByCertificateId(certificateEntity.getId());
        return certificateEntity.toCertificate(subjectEntities);
    }
}
