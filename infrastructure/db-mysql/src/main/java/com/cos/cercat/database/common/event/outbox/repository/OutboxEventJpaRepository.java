package com.cos.cercat.database.common.event.outbox.repository;

import com.cos.cercat.database.common.event.outbox.entity.OutboxEventEntity;
import com.cos.cercat.domain.common.event.EventStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OutboxEventJpaRepository extends JpaRepository<OutboxEventEntity, String> {

    @Query("SELECT f FROM OutboxEventEntity f WHERE f.status = :status ORDER BY f.createdAt ASC")
    List<OutboxEventEntity> findUnprocessed(EventStatus status);


}
