package com.cos.cercat.database.common.event.inbox.repository;

import com.cos.cercat.database.common.event.inbox.entity.InboxEventEntity;
import com.cos.cercat.domain.common.event.EventStatus;
import com.cos.cercat.domain.common.event.inbox.InboxEvent;
import com.cos.cercat.domain.common.event.inbox.InboxEventRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional
public class InboxEventRepositoryAdapter implements InboxEventRepository {

    private final InboxEventJpaRepository inboxEventJpaRepository;

    @Override
    public void save(InboxEvent inboxEvent) {
        inboxEventJpaRepository.save(InboxEventEntity.from(inboxEvent));
    }

    @Override
    public boolean exists(String id) {
        return inboxEventJpaRepository.existsById(id);
    }

    @Override
    public List<InboxEvent> findByStatus(EventStatus status) {
        return inboxEventJpaRepository.findByStatusOrderByCreatedAtAsc(status).stream()
                .map(InboxEventEntity::toDomain)
                .toList();
    }
}
