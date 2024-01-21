package com.cos.cercat.board.repository;

import com.cos.cercat.board.domain.TipPost;
import com.cos.cercat.certificate.domain.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipPostRepository extends JpaRepository<TipPost, Long>, PostRepositoryCustom<TipPost> {

    List<TipPost> findTop3ByCertificateOrderByLikeCountDesc(Certificate certificate);

}
