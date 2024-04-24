package com.cos.cercat.apis.like.api;

import com.cos.cercat.apis.like.request.ReadLikeStatusRequest;
import com.cos.cercat.common.domain.Response;
import com.cos.cercat.domain.like.LikeService;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class LikApi implements LikeApiDocs {

    private final LikeService likeService;

    @PostMapping("/likes")
    public Response<Void> flipPostLike(ReadLikeStatusRequest request,
                                       @AuthenticationPrincipal UserDTO currentUser) {
        likeService.flipLike(TargetUser.from(currentUser.getId()), request.toLike());
        return Response.success("게시글 좋아요/취소 성공");
    }

    @GetMapping("likes/status")
    public Response<Boolean> isLiked(ReadLikeStatusRequest request,
                                       @AuthenticationPrincipal UserDTO currentUser) {
        return Response.success(likeService.isLiked(TargetUser.from(currentUser.getId()), request.toLike()));
    }

}
