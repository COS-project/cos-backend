package com.cos.cercat.database.certificate.repository;


import com.cos.cercat.database.certificate.entity.CertificateEntity;
import com.cos.cercat.database.certificate.entity.SubjectEntity;
import com.cos.cercat.domain.certificate.*;
import com.cos.cercat.domain.certificate.exception.CertificateNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
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
    public Certificate findById(CertificateId certificateId) {
        CertificateEntity certificateEntity = certificateJpaRepository.findById(certificateId.value())
                .orElseThrow(() -> CertificateNotFoundException.EXCEPTION);

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
    public List<Certificate> find(List<CertificateId> certificateIds) {
        return certificateJpaRepository.findAllById(certificateIds.stream()
                .map(CertificateId::value)
                .toList())
                .stream()
                .map(CertificateEntity::toDomain)
                .toList();
    }

    @Override
    public List<Subject> findSubject(Certificate certificate) {
        return subjectJpaRepository.findSubjectsByCertificateId(certificate.id().value())
                .stream()
                .map(SubjectEntity::toDomain)
                .toList();
    }
}
