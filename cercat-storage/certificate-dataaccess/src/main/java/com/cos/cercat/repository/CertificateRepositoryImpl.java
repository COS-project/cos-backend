package com.cos.cercat.repository;

import com.cos.cercat.certificate.*;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.SubjectEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CertificateRepositoryImpl implements CertificateRepository {

    private final CertificateJpaRepository certificateJpaRepository;
    private final SubjectJpaRepository subjectJpaRepository;

    @Override
    public List<Certificate> findAll() {
        return certificateJpaRepository.findAll().stream()
                .map(CertificateEntity::toDomain)
                .toList();
    }

    @Override
    public Certificate findById(TargetCertificate targetCertificate) {
        CertificateEntity certificateEntity = certificateJpaRepository.findById(targetCertificate.certificateId())
                .orElseThrow(() -> new CustomException(ErrorCode.CERTIFICATE_NOT_FOUND));

        return certificateEntity.toDomain();
    }

    @Override
    public void save(String certificateName, List<SubjectInfo> subjectsInfo) {
        List<SubjectEntity> subjectEntities = subjectsInfo.stream()
                .map(SubjectEntity::from)
                .toList();

        CertificateEntity certificateEntity = CertificateEntity.from(certificateName);
        subjectEntities.forEach(subjectEntity -> subjectEntity.setCertificateEntity(certificateEntity));

        certificateJpaRepository.save(certificateEntity);
        subjectJpaRepository.saveAll(subjectEntities);
    }

    @Override
    public List<Certificate> find(List<TargetCertificate> targetCertificates) {
        return certificateJpaRepository.findAllById(targetCertificates.stream()
                .map(TargetCertificate::certificateId)
                .toList())
                .stream()
                .map(CertificateEntity::toDomain)
                .toList();
    }

    @Override
    public List<Subject> findSubject(Certificate certificate) {
        return subjectJpaRepository.findSubjectsByCertificateId(certificate.id())
                .stream()
                .map(SubjectEntity::toDomain)
                .toList();
    }
}
