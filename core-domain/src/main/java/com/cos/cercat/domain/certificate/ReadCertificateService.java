package com.cos.cercat.domain.certificate;

import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReadCertificateService {

    private final CertificateReader certificateReader;
    private final CertificateExamReader certificateExamReader;
    private final InterestCertificateManager interestCertificateManager;
    private final UserReader userReader;

    /**
     * 자격증 ID를 통해 자격증 상세정보를 조회한다
     *
     * @param targetCertificate 자격증 ID
     * @return 자격증 상세 정보
     */
    public CertificateExam readRecentCertificateExam(TargetCertificate targetCertificate) {
        Certificate certificate = certificateReader.read(targetCertificate);
        return certificateExamReader.readNext(certificate);
    }

    /**
     * 전체 자격증 리스트를 조횧한다.
     * @return 자격증 리스트
     */
    public List<Certificate> readCertificates() {
        return certificateReader.readAll();
    }

    public List<InterestCertificate> readInterestCertificates(TargetUser targetUser) {
        User user = userReader.read(targetUser);
        return interestCertificateManager.find(user);
    }
}
