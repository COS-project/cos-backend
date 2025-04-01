package com.cos.cercat.database.common.repository;

import com.cos.cercat.database.common.entity.FailedEventEntity;
import com.cos.cercat.domain.common.FailedEvent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FailedEventJpaRepository extends JpaRepository<FailedEventEntity, Long> {

    @Query("SELECT f FROM FailedEventEntity f WHERE f.status = :status ORDER BY f.createdAt ASC LIMIT :limit")
    List<FailedEventEntity> findByStatusOrderByCreatedAtAsc(
            @Param("status") FailedEvent.Status status,
            @Param("limit") int limit);

}
