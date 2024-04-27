package com.cos.cercat.certificate;

import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import com.cos.cercat.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateCertificateService {
    private final UserReader userReader;
    private final CertificateFinder certificateFinder;
    private final CertificateAppender certificateAppender;
    private final CertificateExamAppender certificateExamAppender;
    private final InterestCertificateManager interestCertificateManager;
    private final IBoardManager boardManager;

    public void createCertificate(String certificateName, List<SubjectInfo> subjectsInfo) {
        certificateAppender.append(certificateName, subjectsInfo);
    }

    public void createCertificateExam(TargetCertificate targetCertificate, NewExamInformation newExamInformation) {
        certificateExamAppender.append(targetCertificate, newExamInformation);
    }

    @Transactional
    public void addInterestCertificates(TargetUser targetUser, InterestTargets interestTargets) {
        User user = userReader.read(targetUser);
        List<Certificate> certificates = certificateFinder.find(interestTargets.certificates());
        List<NewInterestCertificate> newInterestCertificates = interestTargets.toNewInterestCertificates(certificates);
        interestCertificateManager.append(user, newInterestCertificates);
        boardManager.favoriteAll(user, certificates);
    }
}
