package com.cos.cercat.favoriteBoard.app;

import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
public class FavoriteBoardFlipService {

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
        User user = userService.getUser(userId);
        Certificate certificate = certificateService.getCertificate(certificateId);

        if (favoriteBoardService.existFavoriteBoard(user, certificate)) {
            favoriteBoardService.deleteFavoriteBoard(user, certificate);
            log.info("user - {}, certificate - {} 자격증 게시판 즐겨찾기 해제", user.getEmail(), certificate.getCertificateName());
            return;
        }
        log.info("user - {}, certificate - {} 자격증 게시판 즐겨찾기", user.getEmail(), certificate.getCertificateName());
        favoriteBoardService.createFavoriteBoard(user, certificate);
    }

}
