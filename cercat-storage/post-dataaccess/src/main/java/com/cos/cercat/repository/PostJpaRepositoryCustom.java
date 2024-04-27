package com.cos.cercat.repository;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.post.CommentaryPostSearchCond;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostJpaRepositoryCustom<T> {
    Slice<T> searchPosts(Pageable pageable, CertificateEntity certificateEntity, CommentaryPostSearchCond cond);
}
