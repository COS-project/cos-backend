package com.cos.cercat.certificate.app;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.dto.request.CertificateCreateRequest;
import com.cos.cercat.certificate.dto.request.ExamInfoCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CertificateCreateService {

    private final CertificateService certificateService;
    private final ExamInfoService examInfoService;

    public void createCertificate(CertificateCreateRequest request) {
        certificateService.createCertificate(request.toEntity());
    }

    public void createExamInfo(Long certificateId, ExamInfoCreateRequest request) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        examInfoService.createExamInfo(request.toEntity(certificate));
    }

}
