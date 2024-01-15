package com.cos.cercat.board.api;

import com.cos.cercat.board.app.PostFetchService;
import com.cos.cercat.board.dto.response.CommentaryPostResponse;
import com.cos.cercat.board.dto.response.CommentaryPostWithCommentsResponse;
import com.cos.cercat.global.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "게시글 조회 API")
public class PostFetchApi {

    private final PostFetchService postFetchService;

    @GetMapping("/{certificateId}/commentary-posts")
    @Operation(summary = "해설 게시글 검색", description = "키워드가 안주어졌을 경우 전체 조회")
    public Response<Slice<CommentaryPostResponse>> getCommentaryPosts(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                                      @PathVariable Long certificateId,
                                                                      @RequestParam String keyword) {

        return Response.success(postFetchService.searchCommentaryPosts(pageable, certificateId, keyword));
    }

    @GetMapping("/commentary-posts/{postId}")
    @Operation(summary = "해설 게시글 상세 조회")
    public Response<CommentaryPostWithCommentsResponse> getCommentaryPostDetail(@PathVariable Long postId) {
        return Response.success(postFetchService.getCommentaryPostDetail(postId));
    }
}
