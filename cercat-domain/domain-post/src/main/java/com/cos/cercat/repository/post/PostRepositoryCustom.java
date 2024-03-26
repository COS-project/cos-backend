package com.cos.cercat.repository.post;

import com.cos.cercat.domain.Certificate;
import com.cos.cercat.dto.CommentaryPostSearchCond;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostRepositoryCustom<T> {
    Slice<T> searchPosts(Pageable pageable, Certificate certificate, CommentaryPostSearchCond cond);
}
