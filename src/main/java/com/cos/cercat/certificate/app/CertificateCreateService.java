package com.cos.cercat.certificate.app;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.dto.request.CertificateCreateRequest;
import com.cos.cercat.certificate.dto.request.ExamInfoCreateRequest;
import com.cos.cercat.certificate.dto.request.InterestCertificateCreateRequest;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CertificateCreateService {

    private final CertificateService certificateService;
    private final ExamInfoService examInfoService;
    private final UserService userService;
    private final InterestCertificateService interestCertificateService;


    public void createCertificate(CertificateCreateRequest request) {
        certificateService.createCertificate(request.toEntity());
    }

    public void createExamInfo(Long certificateId, ExamInfoCreateRequest request) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        examInfoService.createExamInfo(request.toEntity(certificate));
    }

    public void createInterestCertificates(List<InterestCertificateCreateRequest> requests, Long userId) {
        User user = userService.getUser(userId);
        requests.forEach(request -> createInterestCertificate(user, request));
        user.updateRole();
    }

    private void createInterestCertificate(User user, InterestCertificateCreateRequest request) {
        Certificate certificate = certificateService.getCertificate(request.certificateId());
        interestCertificateService.createInterestCertificate(request.toEntity(certificate, user));
    }

}
