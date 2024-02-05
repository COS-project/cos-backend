package com.cos.cercat.favoriteBoard.app;

import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
public class FavoriteBoardFlipService {

    private final UserService userService;
    private final CertificateService certificateService;
    private final FavoriteBoardService favoriteBoardService;

    @Transactional
    public void flipFavoriteBoard(Long userId, Long certificateId) {
        User user = userService.getUser(userId);
        Certificate certificate = certificateService.getCertificate(certificateId);

        if (favoriteBoardService.existFavoriteBoard(user, certificate)) {
            favoriteBoardService.deleteFavoriteBoard(user, certificate);
            return;
        }

        favoriteBoardService.createFavoriteBoard(user, certificate);
    }

}
