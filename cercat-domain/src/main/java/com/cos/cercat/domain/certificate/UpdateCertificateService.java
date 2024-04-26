package com.cos.cercat.domain.certificate;

import com.cos.cercat.domain.board.BoardManager;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateCertificateService {

    private final UserReader userReader;
    private final InterestCertificateManager interestCertificateManager;
    private final BoardManager boardManager;
    private final CertificateFinder certificateFinder;

    public void updateCertificateExam(TargetUser targetUser, InterestTargets interestTargets) {

        User user = userReader.read(targetUser);
        List<Certificate> interested = interestCertificateManager.findCertificate(user);
        interestCertificateManager.removeAll(user);
        boardManager.unfavoriteAll(user, interested);

        List<Certificate> interesting = certificateFinder.find(interestTargets.certificates());
        List<NewInterestCertificate> newInterestCertificates = interestTargets.toNewInterestCertificates(interesting);
        interestCertificateManager.append(user, newInterestCertificates);
        boardManager.favoriteAll(user, interesting);
    }

}
