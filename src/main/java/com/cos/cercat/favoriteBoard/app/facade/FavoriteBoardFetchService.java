package com.cos.cercat.favoriteBoard.app.facade;

import com.cos.cercat.certificate.app.CertificateService;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.dto.response.CertificateResponse;
import com.cos.cercat.favoriteBoard.app.FavoriteBoardService;
import com.cos.cercat.favoriteBoard.domain.FavoriteBoard;
import com.cos.cercat.favoriteBoard.dto.response.BoardResponse;
import com.cos.cercat.user.app.UserService;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class FavoriteBoardFetchService {

    private final FavoriteBoardService favoriteBoardService;
    private final CertificateService certificateService;
    private final UserService userService;

    /***
     * 즐겨찾기 여부를 포함한 자격증 게시판을 조회합니다.
     * @param userId 유저 ID
     * @return 리스트 형태의 게시판 Reponse DTO
     */
    public List<BoardResponse> getBoards(Long userId) {
        User user = userService.getUser(userId);

        List<Long> favoriteBoardIds = favoriteBoardService.getFavoriteBoard(user).stream()
                .map(favoriteBoard -> favoriteBoard.getFavoriteBoardPK().getCertificateId())
                .toList();

        List<Certificate> certificates = certificateService.getCertificates();

        log.info("user - {} 게시판 조회", user.getEmail());

        return certificates.stream()
                .map(certificate -> {
                    if (favoriteBoardIds.contains(certificate.getId())) {
                        return BoardResponse.of(certificate, true);
                    }
                    return BoardResponse.of(certificate, false);
                }).toList();
    }

}
