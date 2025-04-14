package com.cos.cercat.database.common.event.inbox.repository;

import com.cos.cercat.database.common.event.inbox.entity.InboxEventEntity;
import com.cos.cercat.domain.common.event.EventStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InboxEventJpaRepository extends JpaRepository<InboxEventEntity, String> {

    List<InboxEventEntity> findByStatusOrderByCreatedAtAsc(EventStatus status);
}
