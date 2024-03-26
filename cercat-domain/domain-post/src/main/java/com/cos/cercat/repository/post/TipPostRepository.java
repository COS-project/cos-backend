package com.cos.cercat.repository.post;

import com.cos.cercat.domain.User;
import com.cos.cercat.domain.post.TipPost;
import com.cos.cercat.domain.Certificate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipPostRepository extends JpaRepository<TipPost, Long> {

    List<TipPost> findTop3ByCertificateOrderByLikeCountDesc(Certificate certificate);

    Slice<TipPost> findTipPostsByUser(User user, Pageable pageable);

}
