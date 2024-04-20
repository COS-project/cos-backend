package com.cos.cercat.repository.post;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.dto.CommentaryPostSearchCond;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostJpaRepositoryCustom<T> {
    Slice<T> searchPosts(Pageable pageable, CertificateEntity certificateEntity, CommentaryPostSearchCond cond);
}
