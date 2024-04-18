package com.cos.cercat.service;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.InterestCertificate;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.embededId.InterestCertificatePK;
import com.cos.cercat.repository.InterestCertificateRepository;
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

    public List<InterestCertificate> getInterestCertificates(UserEntity userEntity) {
        return interestCertificateRepository.findInterestCertificatesByUserEntity(userEntity);
    }

    public void deleteAllInterestCertificates(UserEntity userEntity) {
        interestCertificateRepository.deleteAllByUserEntity(userEntity);
    }

    public void delete(UserEntity userEntity, CertificateEntity certificateEntity) {
        interestCertificateRepository.deleteById(InterestCertificatePK.of(certificateEntity.getId(), userEntity.getId()));
    }

}
