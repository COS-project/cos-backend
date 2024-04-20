package com.cos.cercat.domain.certificate;

import com.cos.cercat.domain.board.BoardManager;
import com.cos.cercat.domain.user.TargetUser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateCertificateService {
    private final CertificateAppender certificateAppender;
    private final CertificateExamAppender certificateExamAppender;
    private final InterestCertificateManager interestCertificateManager;
    private final BoardManager boardManager;

    public void createCertificate(String certificateName, List<SubjectInfo> subjectsInfo) {
        certificateAppender.append(certificateName, subjectsInfo);
    }

    public void createCertificateExam(TargetCertificate targetCertificate, ExamInformation examInformation) {
        certificateExamAppender.append(targetCertificate, examInformation);
    }

    @Transactional
    public void addInterestCertificates(TargetUser targetUser, InterestTargets interestTargets) {
        interestCertificateManager.append(targetUser, interestTargets);
        boardManager.favoriteAll(targetUser, interestTargets.certificates());
    }
}
