package com.cos.cercat.apis.board.api;

import com.cos.cercat.apis.board.response.BoardResponse;
import com.cos.cercat.domain.board.BoardService;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class BoardApi implements BoardApiDocs {

    private final BoardService boardService;

    @PostMapping("/favorite-boards/{certificateId}")
    public Response<Void> flipFavoriteBoard(@PathVariable Long certificateId,
                                            @AuthenticationPrincipal User currentUser) {
        boardService.flipFavorite(TargetUser.from(currentUser.getId()), TargetCertificate.from(certificateId));
        return Response.success("게시판 즐겨찾기/해제 성공");
    }

    @GetMapping("/boards")
    public Response<List<BoardResponse>> readBoards(@AuthenticationPrincipal User currentUser) {
        return Response.success(boardService.read().stream()
                .map(board -> {
                    boolean isFavorite = boardService.isFavorite(TargetUser.from(currentUser.getId()), TargetCertificate.from(board.certificateId()));
                    return BoardResponse.of(board, isFavorite);
                })
                .toList());
    }




}
