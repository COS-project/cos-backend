package com.cos.cercat.apis.board.api;

import com.cos.cercat.apis.board.app.usecase.FavoriteBoardFetchUseCase;
import com.cos.cercat.apis.board.dto.response.BoardResponse;
import com.cos.cercat.common.dto.Response;
import com.cos.cercat.domain.user.dto.UserDTO;
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

    private final FavoriteBoardFetchUseCase favoriteBoardFetchUseCase;

    @GetMapping("/boards")
    @Operation(summary = "게시판 목록 조회")
    public Response<List<BoardResponse>> getBoards(@AuthenticationPrincipal UserDTO user) {
        return Response.success(favoriteBoardFetchUseCase.getBoards(user.getId()));
    }
}
