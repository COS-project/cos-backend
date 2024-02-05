package com.cos.cercat.favoriteBoard.app;

import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.dto.response.CertificateResponse;
import com.cos.cercat.favoriteBoard.domain.FavoriteBoard;
import com.cos.cercat.favoriteBoard.dto.response.BoardResponse;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteBoardFetchService {

    private final FavoriteBoardService favoriteBoardService;
    private final CertificateService certificateService;
    private final UserService userService;

    public List<BoardResponse> getBoards(Long userId) {
        User user = userService.getUser(userId);

        List<Long> favoriteBoardIds = favoriteBoardService.getFavoriteBoard(user).stream()
                .map(favoriteBoard -> favoriteBoard.getFavoriteBoardPK().getCertificateId())
                .toList();

        List<Certificate> certificates = certificateService.getCertificates();

        return certificates.stream()
                .map(certificate -> {
                    if (favoriteBoardIds.contains(certificate.getId())) {
                        return BoardResponse.of(certificate, true);
                    }
                    return BoardResponse.of(certificate, false);
                }).toList();
    }

}
