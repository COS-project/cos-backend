package com.cos.cercat.domain.certificate;

import com.cos.cercat.domain.board.BoardManager;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateCertificateService {
    
    private final InterestCertificateManager interestCertificateManager;
    private final BoardManager boardManager;

    public void updateCertificateExam(TargetUser targetUser, InterestTargets interestTargets) {
        List<Certificate> interested = interestCertificateManager.readCertificates(targetUser);
        interestCertificateManager.removeAll(targetUser);

        List<TargetCertificate> targetCertificates = toTargetCertificates(interested);
        boardManager.unfavoriteAll(targetUser, targetCertificates);

        interestCertificateManager.append(targetUser, interestTargets);
        boardManager.favoriteAll(targetUser, interestTargets.certificates());
    }

    private static List<TargetCertificate> toTargetCertificates(List<Certificate> certificates) {
        return certificates.stream().map(certificate -> TargetCertificate.from(certificate.id())).toList();
    }
}
