package com.cos.cercat.like.api;

import com.cos.cercat.global.Response;
import com.cos.cercat.like.app.facade.LikeFlipService;
import com.cos.cercat.like.dto.request.LikeType;
import com.cos.cercat.user.dto.UserDTO;
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

    private final LikeFlipService likeFlipService;

    @PostMapping("/{likeType}/likes/{id}")
    @Operation(summary = "좋아요 생성 및 취소")
    public Response<Void> flipPostLike(@PathVariable LikeType likeType,
                                       @PathVariable Long id,
                                       @AuthenticationPrincipal UserDTO user) {
        likeFlipService.flipLike(likeType, id, user.getId());
        return Response.success("게시글 좋아요/취소 성공");
    }

}
