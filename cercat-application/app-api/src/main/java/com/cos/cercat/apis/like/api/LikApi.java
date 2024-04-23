package com.cos.cercat.apis.like.api;

import com.cos.cercat.apis.like.dto.request.ReadLikeStatusRequest;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.domain.like.LikeService;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
@Tag(name = "좋아요 생성/취소 API")
public class LikApi {

    private final LikeService likeService;

    @PostMapping("/likes")
    @Operation(summary = "좋아요 생성 및 취소")
    public Response<Void> flipPostLike(ReadLikeStatusRequest request,
                                       @AuthenticationPrincipal UserDTO currentUser) {
        likeService.flipLike(TargetUser.from(currentUser.getId()), request.toLike());
        return Response.success("게시글 좋아요/취소 성공");
    }

    @GetMapping("likes/status")
    @Operation(summary = "좋아요의 여부 확인")
    public Response<Boolean> isLiked(ReadLikeStatusRequest request,
                                       @AuthenticationPrincipal UserDTO currentUser) {
        return Response.success(likeService.isLiked(TargetUser.from(currentUser.getId()), request.toLike()));
    }

}
