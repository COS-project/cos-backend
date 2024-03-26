package com.cos.cercat.service;


import com.cos.cercat.domain.Certificate;
import com.cos.cercat.repository.CertificateRepository;
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

    private final CertificateRepository certificateRepository;

    public Certificate getCertificate(Long certificateId) {
        return certificateRepository.findById(certificateId).orElseThrow(() ->
                new CustomException(ErrorCode.CERTIFICATE_NOT_FOUND));
    }

    public void createCertificate(Certificate certificate) {
        certificateRepository.save(certificate);
    }

    public List<Certificate> getCertificates() {
        return certificateRepository.findAll();
    }
}
