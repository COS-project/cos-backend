package com.cos.cercat.apis.like.api;

import com.cos.cercat.apis.like.request.LikeRequest;
import com.cos.cercat.domain.like.LikeService;
import com.cos.cercat.domain.like.LikeStatus;
import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.web.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class LikApi implements LikeApiDocs {

    private final LikeService likeService;

    @PostMapping("/likes")
    public Response<Void> like(LikeRequest request,
                                       @AuthenticationPrincipal User currentUser) {
        likeService.like(UserId.from(currentUser.getId()), request.toLike());
        return Response.success("게시글 좋아요/취소 성공");
    }

    @DeleteMapping("/likes")
    public Response<Void> unLike(LikeRequest request,
                                         @AuthenticationPrincipal User currentUser) {
        likeService.unLike(UserId.from(currentUser.getId()), request.toLike());
        return Response.success("게시글 좋아요/취소 성공");
    }

    @GetMapping("likes/status")
    public Response<LikeStatus> likeStatus(LikeRequest request, @AuthenticationPrincipal User currentUser) {
        return Response.success(likeService.getLikeStatus(UserId.from(currentUser.getId()), request.toLike()));
    }

}
