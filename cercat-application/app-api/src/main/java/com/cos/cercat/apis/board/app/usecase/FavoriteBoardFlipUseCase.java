package com.cos.cercat.apis.board.app.usecase;

import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.service.CertificateService;
import com.cos.cercat.service.FavoriteBoardService;
import com.cos.cercat.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;


@UseCase
@Slf4j
@RequiredArgsConstructor
public class FavoriteBoardFlipUseCase {

    private final UserService userService;
    private final CertificateService certificateService;
    private final FavoriteBoardService favoriteBoardService;

    /***
     * 게시판을 즐겨찾기하거나 취소합니다.
     * @param userId 유저 ID
     * @param certificateId 자격증 ID
     */
    @Transactional
    public void flipFavoriteBoard(Long userId, Long certificateId) {
        UserEntity userEntity = userService.getUser(userId);
        CertificateEntity certificateEntity = certificateService.getCertificate(certificateId);

        if (favoriteBoardService.existFavoriteBoard(userEntity, certificateEntity)) {
            favoriteBoardService.deleteFavoriteBoard(userEntity, certificateEntity);
            log.info("userEntity - {}, certificateEntity - {} 자격증 게시판 즐겨찾기 해제", userEntity.getEmail(), certificateEntity.getCertificateName());
            return;
        }
        log.info("userEntity - {}, certificateEntity - {} 자격증 게시판 즐겨찾기", userEntity.getEmail(), certificateEntity.getCertificateName());
        favoriteBoardService.createFavoriteBoard(userEntity, certificateEntity);
    }

}
