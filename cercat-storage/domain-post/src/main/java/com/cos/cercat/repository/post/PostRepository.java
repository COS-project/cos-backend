package com.cos.cercat.repository.post;

import com.cos.cercat.domain.post.PostEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "delete from Image i where i.imageUrl in :imageUrls")
    void deleteAllByImageUrl(List<String> imageUrls);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from PostEntity p where p.id = :id")
    Optional<PostEntity> findByIdWithPessimisticLock(Long id);

}
