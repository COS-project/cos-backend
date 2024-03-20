package com.cos.cercat.favoriteBoard.api;

import com.cos.cercat.favoriteBoard.app.facade.FavoriteBoardFetchService;
import com.cos.cercat.favoriteBoard.dto.response.BoardResponse;
import com.cos.cercat.global.Response;
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

    private final FavoriteBoardFetchService favoriteBoardFetchService;

    @GetMapping("/boards")
    @Operation(summary = "게시판 목록 조회")
    public Response<List<BoardResponse>> getBoards(@AuthenticationPrincipal UserDTO user) {
        return Response.success(favoriteBoardFetchService.getBoards(user.getId()));
    }
}
