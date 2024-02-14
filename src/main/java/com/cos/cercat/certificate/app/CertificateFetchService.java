package com.cos.cercat.certificate.app;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.domain.CertificateExam;
import com.cos.cercat.certificate.dto.response.CertificateExamResponse;
import com.cos.cercat.certificate.dto.response.CertificateResponse;
import com.cos.cercat.certificate.dto.response.InterestCertificateResponse;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CertificateFetchService {

    private final CertificateService certificateService;
    private final CertificateExamService certificateExamService;
    private final InterestCertificateService interestCertificateService;
    private final UserService userService;

    /**
     * 자격증 ID를 통해 자격증 상세정보를 조회한다
     *
     * @param certificateId 자격증 ID
     * @return 자격증 상세 정보
     */
    public CertificateExamResponse getCertificateExam(Long certificateId) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        CertificateExam recentCertificateExam = certificateExamService.getRecentCertificateExam(certificate);

        log.info("certificate - {} 자격증 시험 정보 조회", certificate.getCertificateName());
        return CertificateExamResponse.from(recentCertificateExam);
    }

    /**
     * 전체 자격증 리스트를 조횧한다.
     * @return 자격증 리스트
     */
    public List<CertificateResponse> getCertificates() {
        log.info("전체 자격증 리스트 조회");
        return certificateService.getCertificates().stream()
                .map(CertificateResponse::from)
                .toList();
    }

    public List<InterestCertificateResponse> getInterestCertificate(Long userId) {
        User user = userService.getUser(userId);
        return interestCertificateService.getInterestCertificates(user).stream()
                .map(InterestCertificateResponse::from)
                .toList();
    }


}
