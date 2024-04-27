package com.cos.cercat.repository;

import com.cos.cercat.domain.NormalPostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NormalPostJpaRepository extends JpaRepository<NormalPostEntity, Long> {

    @Query("""
        SELECT np
        FROM NormalPostEntity np
        WHERE np.userEntity.id = :userId
        """)
    Slice<NormalPostEntity> findNormalPostsByUserId(Long userId, Pageable pageable);

}
