package com.cos.cercat.post.repository;

import com.cos.cercat.post.domain.TipPost;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipPostRepository extends JpaRepository<TipPost, Long> {

    List<TipPost> findTop3ByCertificateOrderByLikeCountDesc(Certificate certificate);

    Slice<TipPost> findTipPostsByUser(User user, Pageable pageable);

}
