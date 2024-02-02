package com.cos.cercat.board.repository;

import com.cos.cercat.board.domain.TipPost;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipPostRepository extends JpaRepository<TipPost, Long>, PostRepositoryCustom<TipPost> {

    List<TipPost> findTop3ByCertificateOrderByLikeCountDesc(Certificate certificate);

    Page<TipPost> findTipPostsByUser(User user, Pageable pageable);

}
