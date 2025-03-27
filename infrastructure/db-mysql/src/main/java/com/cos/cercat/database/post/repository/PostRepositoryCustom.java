package com.cos.cercat.database.post.repository;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.post.Post;
import com.cos.cercat.domain.searchlog.SearchCond;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostRepositoryCustom {
    Slice<Post> findPosts(SearchCond cond, Certificate certificate, Pageable pageable);
}
