package com.cos.cercat.domain.certificate;

import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InterestCertificateManager {

    private final InterestCertificateRepository interestCertificateRepository;

    @Transactional
    public void append(TargetUser targetUser, InterestTargets interestTargets) {
        interestCertificateRepository.saveAll(targetUser, interestTargets);
    }

    public List<InterestCertificate> read(TargetUser targetUser) {
        return interestCertificateRepository.findAll(targetUser);
    }

    public List<Certificate> readCertificates(TargetUser targetUser) {
        return read(targetUser).stream()
                .map(InterestCertificate::certificate)
                .toList();
    }

    @Transactional
    public void remove(TargetUser targetUser, TargetCertificate targetCertificate) {
        interestCertificateRepository.remove(targetUser, targetCertificate);
    }

    @Transactional
    public void removeAll(TargetUser targetUser) {
        interestCertificateRepository.removeAll(targetUser);
    }
}
