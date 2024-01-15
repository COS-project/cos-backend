package com.cos.cercat.board.dto.request;

import java.util.List;

public record CommentaryPostUpdateRequest(
        Long postId,
        String title,
        String content,
        Integer examYear,
        Integer round,
        Integer questionSequence,
        List<String> removeImageUrls
) {
}
