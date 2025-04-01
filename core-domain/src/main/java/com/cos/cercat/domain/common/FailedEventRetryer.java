package com.cos.cercat.domain.common;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FailedEventRetryer {

    private final EventPublisher eventPublisher;
    private final FailedEventManager failedEventManager;

    @Value("${kafka.producer.max-manual-retries}")
    private int maxManualRetries;

    public void retry() {
        try {
            log.debug("실패 이벤트 정기 재시도 작업 시작");
            processEvents(500);
        } catch (Exception e) {
            log.error("실패 이벤트 재시도 중 오류 발생", e);
        }
    }

    public void processEvents(int batchSize) {
        List<FailedEvent> pendingEvents = failedEventManager.readPendingEvents(batchSize);

        if (pendingEvents.isEmpty()) {
            log.debug("재시도할 실패 이벤트가 없습니다");
            return;
        }

        filterUnrecoverableEvents(pendingEvents);

        log.debug("재시도 대상 이벤트 {}건 발견", pendingEvents.size());

        for (FailedEvent event : pendingEvents) {
            processEvent(event);
        }
    }

    private void filterUnrecoverableEvents(List<FailedEvent> pendingEvents) {
        pendingEvents.removeIf(failedEvent -> {
            if (failedEvent.getRetryCount() > maxManualRetries * 2) {
                failedEventManager.updateAsPermanentlyFailed(failedEvent);
                return true;
            }
            return false;
        });
    }

    private void processEvent(FailedEvent failedEvent) {
        log.debug("[Event:{}] 재시도 시작 (type={}, count={})",
                failedEvent.getId(), failedEvent.getEventType(), failedEvent.getRetryCount());
        Event originalEvent = failedEvent.getEvent();
        eventPublisher.retry(originalEvent, failedEvent.getRetryCount() + 1);
        failedEventManager.updateAsProcessed(failedEvent);
    }


}
