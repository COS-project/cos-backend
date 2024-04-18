package com.cos.cercat.apis.board.api;

import com.cos.cercat.apis.board.dto.response.BoardResponse;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.domain.board.BoardService;
import com.cos.cercat.domain.board.TargetBoard;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "게시판 목록 조회 API")
public class FavoriteBoardFetchApi {

    private final BoardService boardService;

    @GetMapping("/boards")
    @Operation(summary = "게시판 목록 조회")
    public Response<List<BoardResponse>> readBoards(@AuthenticationPrincipal UserDTO currentUser) {
        return Response.success(boardService.read().stream()
                .map(board -> {
                    boolean isFavorite = boardService.isFavorite(TargetBoard.from(board.certificateId(), currentUser.getId()));
                    return BoardResponse.of(board, isFavorite);
                })
                .toList());
    }


}
