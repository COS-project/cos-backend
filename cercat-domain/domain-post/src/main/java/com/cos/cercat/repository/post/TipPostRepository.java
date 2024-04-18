package com.cos.cercat.repository.post;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.post.TipPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipPostRepository extends JpaRepository<TipPost, Long> {

    List<TipPost> findTop3ByCertificateEntityOrderByLikeCountDesc(CertificateEntity certificateEntity);

    Slice<TipPost> findTipPostsByUserEntity(UserEntity userEntity, Pageable pageable);

}
