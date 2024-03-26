package com.cos.cercat.apis.board.app.usecase;

import com.cos.cercat.apis.board.dto.response.BoardResponse;
import com.cos.cercat.common.annotation.UseCase;
import com.cos.cercat.domain.certificate.service.CertificateService;
import com.cos.cercat.domain.certificate.domain.Certificate;
import com.cos.cercat.domain.board.service.FavoriteBoardService;
import com.cos.cercat.domain.user.service.UserService;
import com.cos.cercat.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class FavoriteBoardFetchUseCase {

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
