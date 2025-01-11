package com.cos.cercat.domain.certificate;

import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCertificateService {

    private final UserReader userReader;
    private final InterestCertificateManager interestCertificateManager;

    public void updateCertificateExam(TargetUser targetUser, InterestTargets interestTargets) {
        User user = userReader.read(targetUser);
        interestCertificateManager.removeAll(user);
        interestCertificateManager.append(user, interestTargets);
    }

}
