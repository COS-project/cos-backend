package com.cos.cercat.board.repository;

import com.cos.cercat.board.domain.CommentaryPost;
import com.cos.cercat.certificate.domain.Certificate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CommentaryPostRepositoryCustom {
    Slice<CommentaryPost> searchCommentaryPosts(Pageable pageable, Certificate certificate, String keyword);
}
