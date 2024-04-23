package com.cos.cercat.apis.board.api;

import com.cos.cercat.apis.board.dto.response.BoardResponse;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.domain.board.BoardService;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
@Tag(name = "게시판 즐겨찾기 생성/취소 API")
public class favoriteBoardApi {

    private final BoardService boardService;

    @PostMapping("/favorite-boards/{certificateId}")
    @Operation(summary = "게시판 즐겨찾기 및 해제")
    public Response<Void> flipFavoriteBoard(@PathVariable Long certificateId,
                                            @AuthenticationPrincipal UserDTO currentUser) {
        boardService.flipFavorite(TargetUser.from(currentUser.getId()), TargetCertificate.from(certificateId));
        return Response.success("게시판 즐겨찾기/해제 성공");
    }

    @GetMapping("/boards")
    @Operation(summary = "게시판 목록 조회")
    public Response<List<BoardResponse>> readBoards(@AuthenticationPrincipal UserDTO currentUser) {
        return Response.success(boardService.read().stream()
                .map(board -> {
                    boolean isFavorite = boardService.isFavorite(TargetUser.from(currentUser.getId()), TargetCertificate.from(board.certificateId()));
                    return BoardResponse.of(board, isFavorite);
                })
                .toList());
    }




}
