package com.cos.cercat.repository;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.FavoriteBoardEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.board.FavoriteBoardRepository;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.embededId.FavoriteBoardPK;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class FavoriteBoardCoreRepository implements FavoriteBoardRepository {

    private final FavoriteBoardJpaRepository favoriteBoardJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final CertificateJpaRepository certificateJpaRepository;

    @Override
    public boolean isFavorite(TargetUser targetUser, TargetCertificate targetCertificate) {
        return favoriteBoardJpaRepository.existsFavoriteBoardByFavoriteBoardPK(FavoriteBoardPK.of(targetUser.userId(), targetCertificate.certificateId()));
    }

    @Override
    @Transactional
    public void save(TargetUser targetUser, TargetCertificate targetCertificate) {
        UserEntity userEntity = userJpaRepository.getReferenceById(targetUser.userId());
        CertificateEntity certificateEntity = certificateJpaRepository.getReferenceById(targetCertificate.certificateId());
        favoriteBoardJpaRepository.save(FavoriteBoardEntity.of(userEntity, certificateEntity));
    }

    @Override
    @Transactional
    public void remove(TargetUser targetUser, TargetCertificate targetCertificate) {
        favoriteBoardJpaRepository.deleteById(targetUser.userId(), targetCertificate.certificateId());
    }
}
