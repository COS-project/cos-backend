package com.cos.cercat.certificate.app;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.dto.request.InterestCertificateUpdateRequest;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateUpdateService {

    private final InterestCertificateService interestCertificateService;
    private final UserService userService;
    private final CertificateService certificateService;

    @Transactional
    public void updateInterestCertificates(List<InterestCertificateUpdateRequest> requests, Long userId) {
        User user = userService.getUser(userId);
        interestCertificateService.deleteAllInterestCertificates(user); // 기존 흥미자격증을 모두 삭제

        requests.forEach(request -> {
            Certificate certificate = certificateService.getCertificate(request.certificateId());
            interestCertificateService.createInterestCertificate(request.toEntity(certificate, user)); //다시 생성
        });
    }

}
