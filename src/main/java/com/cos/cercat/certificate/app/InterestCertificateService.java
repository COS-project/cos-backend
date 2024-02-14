package com.cos.cercat.certificate.app;

import com.cos.cercat.certificate.domain.InterestCertificate;
import com.cos.cercat.certificate.repository.InterestCertificateRepository;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class InterestCertificateService {

    private final InterestCertificateRepository interestCertificateRepository;

    public void createInterestCertificate(InterestCertificate interestCertificate) {
        interestCertificateRepository.save(interestCertificate);
    }

    public List<InterestCertificate> getInterestCertificates(User user) {
        return interestCertificateRepository.findInterestCertificatesByUser(user);
    }

    public void deleteAllInterestCertificates(User user) {
        interestCertificateRepository.deleteAllByUser(user);
    }

}
