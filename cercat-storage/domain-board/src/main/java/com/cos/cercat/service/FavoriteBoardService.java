package com.cos.cercat.service;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.FavoriteBoardEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.embededId.FavoriteBoardPK;
import com.cos.cercat.repository.FavoriteBoardJpaRepository;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class FavoriteBoardService {

    private final FavoriteBoardJpaRepository favoriteBoardJpaRepository;

    public boolean existFavoriteBoard(UserEntity userEntity, CertificateEntity certificateEntity) {
        return favoriteBoardJpaRepository.existsFavoriteBoardByFavoriteBoardPK(FavoriteBoardPK.of(userEntity.getId(), certificateEntity.getId()));
    }

    public void createFavoriteBoard(UserEntity userEntity, CertificateEntity certificateEntity) {
        favoriteBoardJpaRepository.save(FavoriteBoardEntity.of(userEntity, certificateEntity));
    }

    public void deleteFavoriteBoard(UserEntity userEntity, CertificateEntity certificateEntity) {
        favoriteBoardJpaRepository.deleteById(FavoriteBoardPK.of(userEntity.getId(), certificateEntity.getId()));
    }

}
