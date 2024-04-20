package com.cos.cercat.service;


import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.repository.CertificateJpaRepository;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CertificateService {

    private final CertificateJpaRepository certificateJpaRepository;

    public CertificateEntity getCertificate(Long certificateId) {
        return certificateJpaRepository.findById(certificateId).orElseThrow(() ->
                new CustomException(ErrorCode.CERTIFICATE_NOT_FOUND));
    }

    public void createCertificate(CertificateEntity certificateEntity) {
        certificateJpaRepository.save(certificateEntity);
    }

    public List<CertificateEntity> getCertificates() {
        return certificateJpaRepository.findAll();
    }
}
