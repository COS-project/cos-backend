package com.cos.cercat.board.api;

import com.cos.cercat.board.app.usecase.FavoriteBoardFetchUseCase;
import com.cos.cercat.board.dto.response.BoardResponse;
import com.cos.cercat.dto.Response;
import com.cos.cercat.user.dto.UserDTO;
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
