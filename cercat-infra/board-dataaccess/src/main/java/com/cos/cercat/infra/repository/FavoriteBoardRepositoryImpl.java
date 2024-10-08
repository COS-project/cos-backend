package com.cos.cercat.infra.repository;

import com.cos.cercat.domain.board.FavoriteBoardRepository;
import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.infra.entity.CertificateEntity;
import com.cos.cercat.infra.entity.FavoriteBoardEntity;
import com.cos.cercat.infra.entity.UserEntity;
import com.cos.cercat.infra.entity.embededId.FavoriteBoardPK;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional
public class FavoriteBoardRepositoryImpl implements FavoriteBoardRepository {

    private final FavoriteBoardJpaRepository favoriteBoardJpaRepository;

    @Override
    public boolean isFavorite(User user, Certificate certificate) {
        UserEntity userEntity = UserEntity.from(user);
        CertificateEntity certificateEntity = CertificateEntity.from(certificate);
        return favoriteBoardJpaRepository.existsFavoriteBoardByFavoriteBoardPK(FavoriteBoardPK.of(userEntity, certificateEntity));
    }

    @Override
    @Transactional
    public void save(User user, Certificate certificate) {
        UserEntity userEntity = UserEntity.from(user);
        CertificateEntity certificateEntity = CertificateEntity.from(certificate);
        favoriteBoardJpaRepository.save(FavoriteBoardEntity.of(userEntity, certificateEntity));
    }

    @Override
    @Transactional
    public void remove(User user, Certificate interested) {
        favoriteBoardJpaRepository.deleteById(user.getId(), interested.id());
    }
}
