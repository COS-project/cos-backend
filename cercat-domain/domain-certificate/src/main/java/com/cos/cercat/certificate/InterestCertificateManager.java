package com.cos.cercat.certificate;

import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InterestCertificateManager {

    private final InterestCertificateRepository interestCertificateRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final CertificateFinder certificateFinder;

    public void append(User user, InterestTargets interestTargets) {
        List<Certificate> interesting = certificateFinder.find(interestTargets.certificates());
        List<NewInterestCertificate> newInterestCertificates = interestTargets.toNewInterestCertificates(interesting);
        interestCertificateRepository.saveAll(user, newInterestCertificates);
        applicationEventPublisher.publishEvent(CreateInterestCertificateEvent.of(user, interesting));
    }

    public List<InterestCertificate> find(User user) {
        return interestCertificateRepository.find(user);
    }

    public List<Certificate> findCertificate(User user) {
        return find(user).stream()
                .map(InterestCertificate::certificate)
                .toList();
    }

    public void remove(User user, Certificate certificate) {
        interestCertificateRepository.remove(user, certificate);
    }

    public void removeAll(User user) {
        List<Certificate> interested = findCertificate(user);
        interestCertificateRepository.removeAll(user);
        applicationEventPublisher.publishEvent(RemoveInterestCertificateEvent.of(user, interested));
    }
}
