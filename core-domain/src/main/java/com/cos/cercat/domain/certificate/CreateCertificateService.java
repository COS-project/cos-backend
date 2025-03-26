package com.cos.cercat.domain.certificate;

import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;
import com.cos.cercat.domain.user.UserUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateCertificateService {
    private final UserReader userReader;
    private final CertificateReader certificateReader;
    private final CertificateAppender certificateAppender;
    private final CertificateExamAppender certificateExamAppender;
    private final InterestCertificateManager interestCertificateManager;
    private final UserUpdater userUpdater;

    public void createCertificate(String certificateName, List<SubjectInfo> subjectsInfo) {
        certificateAppender.append(certificateName, subjectsInfo);
    }

    public void createCertificateExam(CertificateId certificateId, NewExamInformation newExamInformation) {
        Certificate certificate = certificateReader.read(certificateId);
        certificateExamAppender.append(certificate, newExamInformation);
    }

    public void addInterestCertificates(UserId userId, InterestTargets interestTargets) {
        User user = userReader.read(userId);
        interestCertificateManager.append(user, interestTargets);
        user.updateRole();
        userUpdater.update(user);
    }
}
