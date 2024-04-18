package com.cos.cercat.apis.certificate.app.usecase;

import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.service.CertificateExamService;
import com.cos.cercat.service.CertificateService;
import com.cos.cercat.service.InterestCertificateService;
import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.CertificateExam;
import com.cos.cercat.apis.certificate.dto.response.CertificateExamResponse;
import com.cos.cercat.apis.certificate.dto.response.CertificateResponse;
import com.cos.cercat.apis.certificate.dto.response.InterestCertificateResponse;
import com.cos.cercat.service.UserService;
import com.cos.cercat.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CertificateFetchUseCase {

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
        CertificateEntity certificateEntity = certificateService.getCertificate(certificateId);
        CertificateExam recentCertificateExam = certificateExamService.getRecentCertificateExam(certificateEntity);

        log.info("certificateEntity - {} 자격증 시험 정보 조회", certificateEntity.getCertificateName());
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
        UserEntity userEntity = userService.getUser(userId);
        return interestCertificateService.getInterestCertificates(userEntity).stream()
                .map(InterestCertificateResponse::from)
                .toList();
    }


}
