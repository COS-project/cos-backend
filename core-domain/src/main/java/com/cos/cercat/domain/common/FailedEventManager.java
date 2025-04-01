package com.cos.cercat.domain.common;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FailedEventManager {

    private final FailedEventRepository failedEventRepository;

    public void append(FailedEvent failedEvent) {
        failedEventRepository.save(failedEvent);
    }

    public FailedEvent read(Long eventId) {
        return failedEventRepository.findById(eventId);
    }

    public List<FailedEvent> readPendingEvents(int batchSize) {
        return failedEventRepository.findByStatusOrderByCreatedAtAsc(FailedEvent.Status.PENDING, batchSize);
    }

    public void updateAsProcessed(FailedEvent failedEvent) {
        failedEvent.markAsProcessed();
        failedEventRepository.save(failedEvent);
    }

    public void updateAsPermanentlyFailed(FailedEvent failedEvent) {
        failedEvent.markAsPermanentlyFailed();
        failedEventRepository.save(failedEvent);
    }

}
