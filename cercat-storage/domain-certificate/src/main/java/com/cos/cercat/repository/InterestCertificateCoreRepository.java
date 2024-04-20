package com.cos.cercat.repository;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.InterestCertificateEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.certificate.*;
import com.cos.cercat.domain.embededId.InterestCertificatePK;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InterestCertificateCoreRepository implements InterestCertificateRepository {

    private final InterestCertificateJpaRepository interestCertificateJpaRepository;
    private final CertificateJpaRepository certificateJpaRepository;
    private final UserJpaRepository userJpaRepository;


    @Override
    public void saveAll(TargetUser targetUser, InterestTargets interestTargets) {
        List<InterestCertificateEntity> interestCertificateEntities = interestTargets.interestTargetList().stream()
                .map(interestTarget -> toEntity(interestTarget, targetUser))
                .toList();

        interestCertificateJpaRepository.saveAll(interestCertificateEntities);
    }

    @Override
    public List<InterestCertificate> findAll(TargetUser targetUser) {
        UserEntity userEntity = userJpaRepository.getReferenceById(targetUser.userId());
        List<InterestCertificateEntity> interestCertificateEntities = interestCertificateJpaRepository.findInterestCertificatesByUserEntity(userEntity);
        return interestCertificateEntities.stream()
                .map(InterestCertificateEntity::toDomain)
                .toList();
    }

    @Override
    public void remove(TargetUser targetUser, TargetCertificate targetCertificate) {
        InterestCertificatePK interestCertificatePK = InterestCertificatePK.of(targetCertificate.certificateId(), targetCertificate.certificateId());
        interestCertificateJpaRepository.deleteById(interestCertificatePK);
    }

    @Override
    public void removeAll(TargetUser targetUser) {
        interestCertificateJpaRepository.deleteAllByUserId(targetUser.userId());
    }

    private InterestCertificateEntity toEntity(InterestTarget interestTarget, TargetUser targetUser) {
        CertificateEntity certificateEntity = certificateJpaRepository.getReferenceById(interestTarget.certificateId());
        UserEntity userEntity = userJpaRepository.getReferenceById(targetUser.userId());

        return InterestCertificateEntity.builder()
                .certificateEntity(certificateEntity)
                .userEntity(userEntity)
                .priority(interestTarget.interestPriority())
                .build();
    }
}
