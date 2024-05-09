package com.cos.cercat.certificate;

import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import com.cos.cercat.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

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
