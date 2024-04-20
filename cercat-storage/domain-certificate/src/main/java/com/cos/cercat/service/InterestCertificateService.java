package com.cos.cercat.service;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.InterestCertificateEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.embededId.InterestCertificatePK;
import com.cos.cercat.repository.InterestCertificateJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class InterestCertificateService {

    private final InterestCertificateJpaRepository interestCertificateJpaRepository;

    public void createInterestCertificate(InterestCertificateEntity interestCertificateEntity) {
        interestCertificateJpaRepository.save(interestCertificateEntity);
    }

    public List<InterestCertificateEntity> getInterestCertificates(UserEntity userEntity) {
        return interestCertificateJpaRepository.findInterestCertificatesByUserEntity(userEntity);
    }

    public void deleteAllInterestCertificates(UserEntity userEntity) {
        interestCertificateJpaRepository.deleteAllByUserId(userEntity.getId());
    }

    public void delete(UserEntity userEntity, CertificateEntity certificateEntity) {
        interestCertificateJpaRepository.deleteById(InterestCertificatePK.of(certificateEntity.getId(), userEntity.getId()));
    }

}
