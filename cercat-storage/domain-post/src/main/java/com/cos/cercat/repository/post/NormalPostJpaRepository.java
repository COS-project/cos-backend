package com.cos.cercat.repository.post;

import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.post.NormalPostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NormalPostJpaRepository extends JpaRepository<NormalPostEntity, Long> {

    Slice<NormalPostEntity> findNormalPostsByUserEntity(UserEntity userEntity, Pageable pageable);

}
