package com.cos.cercat.certificate.app;


import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.repository.CertificateRepository;
import com.cos.cercat.global.exception.CustomException;
import com.cos.cercat.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CertificateService {

    private final CertificateRepository certificateRepository;

    public Certificate getCertificate(Long certificateId) {
        return certificateRepository.findById(certificateId).orElseThrow(() ->
                new CustomException(ErrorCode.CERTIFICATE_NOT_FOUND));
    }

}