package com.cos.cercat.infra.repository;

import com.cos.cercat.infra.entity.CertificateEntity;
import com.cos.cercat.domain.post.CommentaryPostSearchCond;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostJpaRepositoryCustom<T> {
    Slice<T> searchPosts(Pageable pageable, CertificateEntity certificateEntity, CommentaryPostSearchCond cond);
}
