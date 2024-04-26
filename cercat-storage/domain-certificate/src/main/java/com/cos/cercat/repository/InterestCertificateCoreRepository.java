package com.cos.cercat.repository;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.InterestCertificateEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.certificate.*;
import com.cos.cercat.domain.embededId.InterestCertificatePK;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;
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
    public void saveAll(User targetUser, List<NewInterestCertificate> newInterestCertificates) {
        List<InterestCertificateEntity> interestCertificateEntities = newInterestCertificates.stream()
                .map(newInterestCertificate -> toEntity(newInterestCertificate, targetUser))
                .toList();

        interestCertificateJpaRepository.saveAll(interestCertificateEntities);
    }

    @Override
    public List<InterestCertificate> find(User user) {
        UserEntity userEntity = userJpaRepository.getReferenceById(user.id());
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
    public void removeAll(User user) {
        interestCertificateJpaRepository.deleteAllByUserId(user.id());
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
