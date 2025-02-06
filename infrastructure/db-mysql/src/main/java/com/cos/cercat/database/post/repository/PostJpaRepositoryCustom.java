package com.cos.cercat.database.post.repository;

import com.cos.cercat.database.certificate.entity.CertificateEntity;
import com.cos.cercat.domain.post.CommentaryPostSearchCond;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostJpaRepositoryCustom<T> {
    Slice<T> searchPosts(Pageable pageable, CertificateEntity certificateEntity, CommentaryPostSearchCond cond);
}
