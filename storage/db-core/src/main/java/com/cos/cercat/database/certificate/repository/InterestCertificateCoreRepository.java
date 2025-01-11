package com.cos.cercat.database.certificate.repository;


import com.cos.cercat.database.certificate.entity.CertificateEntity;
import com.cos.cercat.database.certificate.entity.InterestCertificateEntity;
import com.cos.cercat.database.user.entity.UserEntity;
import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.InterestCertificate;
import com.cos.cercat.domain.certificate.InterestCertificateRepository;
import com.cos.cercat.domain.certificate.NewInterestCertificate;
import com.cos.cercat.database.certificate.entity.embededId.InterestCertificatePK;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class InterestCertificateCoreRepository implements InterestCertificateRepository {

    private final InterestCertificateJpaRepository interestCertificateJpaRepository;

    @Override
    public void saveAll(User user, List<NewInterestCertificate> newInterestCertificates) {
        List<InterestCertificateEntity> interestCertificateEntities = newInterestCertificates.stream()
                .map(newInterestCertificate -> toEntity(newInterestCertificate, user))
                .toList();

        interestCertificateJpaRepository.saveAll(interestCertificateEntities);
    }

    @Override
    public List<InterestCertificate> find(User user) {
        List<InterestCertificateEntity> interestCertificateEntities = interestCertificateJpaRepository.findInterestCertificatesByUserId(user.getId());
        return interestCertificateEntities.stream()
                .map(InterestCertificateEntity::toDomain)
                .toList();
    }

    @Override
    public void remove(User user, Certificate certificate) {
        UserEntity userEntity = UserEntity.from(user);
        CertificateEntity certificateEntity = CertificateEntity.from(certificate);
        InterestCertificatePK interestCertificatePK = InterestCertificatePK.of(certificateEntity, userEntity);
        interestCertificateJpaRepository.deleteById(interestCertificatePK);
    }

    @Override
    public void removeAll(User user) {
        interestCertificateJpaRepository.deleteAllByUserId(user.getId());
    }

    private InterestCertificateEntity toEntity(NewInterestCertificate newInterestCertificate, User user) {
        UserEntity userEntity = UserEntity.from(user);
        CertificateEntity certificateEntity = CertificateEntity.from(newInterestCertificate.certificate());
        return InterestCertificateEntity.builder()
                .certificateEntity(certificateEntity)
                .userEntity(userEntity)
                .priority(newInterestCertificate.interestPriority())
                .build();
    }
}
