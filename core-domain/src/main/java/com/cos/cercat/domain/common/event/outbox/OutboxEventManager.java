package com.cos.cercat.domain.common.event.outbox;

import com.cos.cercat.domain.common.event.EventStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OutboxEventManager {

    private final OutboxEventRepository outboxEventRepository;

    public void append(OutboxEvent outboxEvent) {
        outboxEventRepository.save(outboxEvent);
    }

    public OutboxEvent read(String eventId) {
        return outboxEventRepository.findById(eventId);
    }

    public List<OutboxEvent> readUnprocessedEvents() {
        return outboxEventRepository.findByStatus(EventStatus.PENDING);
    }
}
