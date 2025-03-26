package com.cos.cercat.apis.board.api;

import com.cos.cercat.apis.board.response.BoardResponse;
import com.cos.cercat.domain.certificate.CertificateId;
import com.cos.cercat.web.Response;
import com.cos.cercat.domain.post.BoardService;
import com.cos.cercat.domain.user.UserId;
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
        boardService.flipFavorite(UserId.from(currentUser.getId()), CertificateId.from(certificateId));
        return Response.success("게시판 즐겨찾기/해제 성공");
    }

    @GetMapping("/boards")
    public Response<List<BoardResponse>> readBoards(@AuthenticationPrincipal User currentUser) {
        return Response.success(boardService.read().stream()
                .map(board -> {
                    boolean isFavorite = boardService.isFavorite(UserId.from(currentUser.getId()), board.certificateId());
                    return BoardResponse.of(board, isFavorite);
                })
                .toList());
    }




}
