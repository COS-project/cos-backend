package com.cos.cercat.repository;

import com.cos.cercat.board.FavoriteBoardRepository;
import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.FavoriteBoardEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.embededId.FavoriteBoardPK;
import com.cos.cercat.user.User;
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
    public boolean isFavorite(User user, Certificate certificate) {
        return favoriteBoardJpaRepository.existsFavoriteBoardByFavoriteBoardPK(FavoriteBoardPK.of(user.getId(), certificate.id()));
    }

    @Override
    @Transactional
    public void save(User user, Certificate certificate) {
        UserEntity userEntity = userJpaRepository.getReferenceById(user.getId());
        CertificateEntity certificateEntity = certificateJpaRepository.getReferenceById(certificate.id());
        favoriteBoardJpaRepository.save(FavoriteBoardEntity.of(userEntity, certificateEntity));
    }

    @Override
    @Transactional
    public void remove(User user, Certificate interested) {
        favoriteBoardJpaRepository.deleteById(user.getId(), interested.id());
    }
}
