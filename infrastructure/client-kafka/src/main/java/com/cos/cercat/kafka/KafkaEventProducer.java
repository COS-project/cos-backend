package com.cos.cercat.kafka;

import com.cos.cercat.domain.common.Event;
import com.cos.cercat.domain.common.EventPublisher;
import com.cos.cercat.domain.common.FailedEvent;
import com.cos.cercat.domain.common.FailedEventManager;
import jakarta.annotation.PreDestroy;
import java.util.concurrent.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaEventProducer implements EventPublisher {

    private final KafkaTemplate<String, Event> kafkaTemplate;
    private final FailedEventManager failedEventManager;
    private final ScheduledExecutorService retryExecutor = Executors.newScheduledThreadPool(2);
    private final ExecutorService asyncExecutor = Executors.newFixedThreadPool(4);

    @Value("${kafka.producer.max-manual-retries}")
    private int maxManualRetries;

    @Override
    public void publish(Event event) {
        CompletableFuture.runAsync(() -> publishWithRetry(event, 0), asyncExecutor);
    }

    @Override
    public void retry(Event event, int retryCount) {
        CompletableFuture.runAsync(() -> publishWithRetry(event, retryCount), asyncExecutor);
    }

    private void publishWithRetry(Event event, int retryCount) {
        try {
            CompletableFuture<SendResult<String, Event>> sentResult =
                    kafkaTemplate.send(event.resolveType(), event.resolveKey(), event);

            sentResult.whenComplete((result, ex) -> {
                if (ex != null) {
                    handleSendExceptionAsync(event, ex, retryCount);
                } else {
                    log.debug(
                            "[이벤트:{}] 토픽 발행 성공 (key={})",
                            event.resolveType(),
                            event.resolveKey()
                    );
                }
            });
        } catch (Exception e) {
            log.error("[이벤트:{}] 토픽 발행 중 오류 발생 (재시도 횟수: {})", event.resolveType(), retryCount, e);
            handleSendExceptionAsync(event, e, retryCount);
        }

    }

    private void handleSendExceptionAsync(Event event, Throwable ex, int retryCount) {
        log.error("[이벤트:{}] 토픽 발행 실패 (재시도 횟수: {})", event.resolveType(), retryCount, ex);

        if (retryCount < maxManualRetries) {
            long backoffMs = (long) Math.pow(2, retryCount) * 1000;
            log.debug("[이벤트:{}] {}ms 후 재시도 예정 (횟수: {}/{})",
                    event.resolveType(), backoffMs, retryCount + 1, maxManualRetries);

            retryExecutor.schedule(
                    () -> publishWithRetry(event, retryCount + 1),
                    backoffMs,
                    TimeUnit.MILLISECONDS
            );
        } else {
            log.error("[이벤트:{}] 최대 재시도 횟수({}) 초과, DB에 저장하여 나중에 재시도 예정",
                    event.resolveType(), maxManualRetries);
            failedEventManager.append(FailedEvent.from(event, retryCount, ex.getMessage()));
        }
    }

    @PreDestroy
    public void shutdown() {
        retryExecutor.shutdown();
        asyncExecutor.shutdown();
    }
}
