package com.cos.cercat.certificate.app;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.domain.CertificateExam;
import com.cos.cercat.certificate.domain.ExamInfo;
import com.cos.cercat.certificate.dto.response.CertificateExamResponse;
import com.cos.cercat.certificate.dto.response.CertificateResponse;
import com.cos.cercat.certificate.dto.response.ExamInfoResponse;
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

    /**
     * 자격증 ID를 통해 자격증 상세정보를 조회한다
     *
     * @param certificateId 자격증 ID
     * @return 자격증 상세 정보
     */
    public CertificateExamResponse getCertificateExam(Long certificateId) {
        Certificate certificate = certificateService.getCertificate(certificateId);
        CertificateExam recentCertificateExam = certificateExamService.getRecentCertificateExam(certificate);

        return CertificateExamResponse.from(recentCertificateExam);
    }

    public Boolean isExamDatePassed(Long certificateId) {
        Certificate certificate = certificateService.getCertificate(certificateId);

        return certificateExamService.isExamDatePassed(certificate);
    }

    /**
     * 전체 자격증 리스트를 조횧한다.
     * @return 자격증 리스트
     */
    public List<CertificateResponse> getCertificates() {
        return certificateService.getCertificates().stream()
                .map(CertificateResponse::from)
                .toList();
    }
}
