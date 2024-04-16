package com.cos.cercat.apis.board.api;

import com.cos.cercat.apis.board.app.usecase.FavoriteBoardFlipUseCase;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "게시판 즐겨찾기 생성/취소 API")
public class FavoriteBoardFlipApi {

    private final FavoriteBoardFlipUseCase favoriteBoardFlipUseCase;

    @PostMapping("/favorite-boards/{certificateId}")
    @Operation(summary = "게시판 즐겨찾기 및 해제")
    public Response<Void> flipFavoriteBoard(@PathVariable Long certificateId,
                                            @AuthenticationPrincipal UserDTO currentUser) {
        favoriteBoardFlipUseCase.flipFavoriteBoard(currentUser.getId(), certificateId);
        return Response.success("게시판 즐겨찾기/해제 성공");
    }


}
