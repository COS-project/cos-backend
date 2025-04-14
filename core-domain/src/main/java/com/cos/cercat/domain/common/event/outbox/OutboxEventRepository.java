package com.cos.cercat.domain.common.event.outbox;

import com.cos.cercat.domain.common.event.EventStatus;
import java.util.List;

/**
 * 실패한 이벤트 저장소에 대한 인터페이스
 */
public interface OutboxEventRepository {

    /**
     * 실패한 이벤트 저장
     */
    OutboxEvent save(OutboxEvent outboxEvent);

    /**
     * 실패한 이벤트 조회
     */
    OutboxEvent findById(String id);

    /**
     * 처리되지않은 이벤트 목록 조회
     */
    List<OutboxEvent> findByStatus(EventStatus status);
}
