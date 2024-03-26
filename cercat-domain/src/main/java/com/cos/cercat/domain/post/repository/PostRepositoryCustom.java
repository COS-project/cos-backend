package com.cos.cercat.domain.post.repository;

import com.cos.cercat.domain.certificate.domain.Certificate;
import com.cos.cercat.domain.post.dto.CommentaryPostSearchCond;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostRepositoryCustom<T> {
    Slice<T> searchPosts(Pageable pageable, Certificate certificate, CommentaryPostSearchCond cond);
}
