package com.cos.cercat.database.common.repository;

import com.cos.cercat.database.common.entity.FailedEventEntity;
import com.cos.cercat.domain.common.FailedEvent;
import com.cos.cercat.domain.common.FailedEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class FailedEventRepositoryAdapter implements FailedEventRepository {

    private final FailedEventJpaRepository jpaRepository;

    @Override
    public FailedEvent save(FailedEvent failedEvent) {
        try {
            FailedEventEntity savedEntity = jpaRepository.save(FailedEventEntity.from(failedEvent));
            return savedEntity.toDomain();
        } catch (Exception e) {
            log.error("FailedEvent 저장 실패: {}", e.getMessage(), e);
            throw new RuntimeException("FailedEvent 저장 실패: " + e.getMessage(), e);
        }
    }

    @Override
    public FailedEvent findById(Long id) {
        return jpaRepository.findById(id)
                .map(FailedEventEntity::toDomain)
                .orElse(null);
    }

    @Override
    public List<FailedEvent> findByStatusOrderByCreatedAtAsc(FailedEvent.Status status, int limit) {
        return jpaRepository.findByStatusOrderByCreatedAtAsc(status, limit).stream()
                .map(FailedEventEntity::toDomain)
                .collect(Collectors.toList());
    }


}
