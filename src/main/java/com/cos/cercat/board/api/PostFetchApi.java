package com.cos.cercat.board.api;

import com.cos.cercat.board.dto.response.CommentaryPostResponse;
import com.cos.cercat.global.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostFetchApi {


    @GetMapping("/{certificateId}/commentary-posts")
    public Response<Slice<CommentaryPostResponse>> getCommentaryPosts(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                                      @PathVariable Long certificateId,
                                                                      @RequestParam String keyword) {
        return null;
    }
}
