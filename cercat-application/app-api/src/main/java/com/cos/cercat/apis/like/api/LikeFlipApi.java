package com.cos.cercat.apis.like.api;

import com.cos.cercat.apis.like.app.usecase.LikeFlipUseCase;
import com.cos.cercat.domain.like.LikeTargetType;
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
@Tag(name = "좋아요 생성/취소 API")
public class LikeFlipApi {

    private final LikeFlipUseCase likeFlipUseCase;

    @PostMapping("/{likeTargetType}/likes/{id}")
    @Operation(summary = "좋아요 생성 및 취소")
    public Response<Void> flipPostLike(@PathVariable LikeTargetType likeTargetType,
                                       @PathVariable Long id,
                                       @AuthenticationPrincipal UserDTO currentUser) {
        likeFlipUseCase.flipLike(likeTargetType, id, currentUser.getId());
        return Response.success("게시글 좋아요/취소 성공");
    }

}
