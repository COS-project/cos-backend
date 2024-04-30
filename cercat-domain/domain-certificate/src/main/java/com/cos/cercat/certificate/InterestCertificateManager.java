package com.cos.cercat.certificate;

import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InterestCertificateManager {

    private final InterestCertificateRepository interestCertificateRepository;

    public void append(User user, List<NewInterestCertificate> newInterestCertificates) {
        interestCertificateRepository.saveAll(user, newInterestCertificates);
    }

    public List<InterestCertificate> find(User user) {
        return interestCertificateRepository.find(user);
    }

    public List<Certificate> findCertificate(User user) {
        return find(user).stream()
                .map(InterestCertificate::certificate)
                .toList();
    }

    @Transactional
    public void remove(User user, Certificate certificate) {
        interestCertificateRepository.remove(user, certificate);
    }

    @Transactional
    public void removeAll(User user) {
        interestCertificateRepository.removeAll(user);
    }
}
