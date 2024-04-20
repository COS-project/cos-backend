package com.cos.cercat.repository.post;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.post.TipPostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipPostRepository extends JpaRepository<TipPostEntity, Long> {

    List<TipPostEntity> findTop3ByCertificateEntityOrderByLikeCountDesc(CertificateEntity certificateEntity);

    Slice<TipPostEntity> findTipPostsByUserEntity(UserEntity userEntity, Pageable pageable);

}
