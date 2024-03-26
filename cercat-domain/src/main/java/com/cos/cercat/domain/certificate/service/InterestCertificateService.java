package com.cos.cercat.domain.certificate.service;

import com.cos.cercat.domain.certificate.domain.Certificate;
import com.cos.cercat.domain.certificate.domain.InterestCertificate;
import com.cos.cercat.domain.certificate.domain.embededId.InterestCertificatePK;
import com.cos.cercat.domain.certificate.repository.InterestCertificateRepository;
import com.cos.cercat.domain.user.domain.User;
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

    public void delete(User user, Certificate certificate) {
        interestCertificateRepository.deleteById(InterestCertificatePK.of(certificate.getId(), user.getId()));
    }

}
