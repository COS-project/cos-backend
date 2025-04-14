package com.cos.cercat.database.common.event.outbox.repository;

import com.cos.cercat.database.common.event.outbox.entity.OutboxEventEntity;
import com.cos.cercat.domain.common.event.EventStatus;
import com.cos.cercat.domain.common.event.outbox.OutboxEvent;
import com.cos.cercat.domain.common.event.outbox.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OutboxEventRepositoryAdapter implements OutboxEventRepository {

    private final OutboxEventJpaRepository jpaRepository;

    @Override
    public OutboxEvent save(OutboxEvent outboxEvent) {
        OutboxEventEntity savedEntity = jpaRepository.save(OutboxEventEntity.from(outboxEvent));
        return savedEntity.toDomain();
    }

    @Override
    public OutboxEvent findById(String id) {
        return jpaRepository.findById(id)
                .map(OutboxEventEntity::toDomain)
                .orElse(null);
    }

    @Override
    public List<OutboxEvent> findByStatus(EventStatus status) {
        return jpaRepository.findUnprocessed(status).stream()
                .map(OutboxEventEntity::toDomain)
                .collect(Collectors.toList());
    }


}
