package com.cos.cercat.domain.common;

import java.util.List;

/**
 * 실패한 이벤트 저장소에 대한 인터페이스
 */
public interface FailedEventRepository {

    /**
     * 실패한 이벤트 저장
     */
    FailedEvent save(FailedEvent failedEvent);

    /**
     * 실패한 이벤트 조회
     */
    FailedEvent findById(Long id);

    /**
     * 특정 상태의 실패 이벤트를 생성 시간 오름차순으로 조회
     *
     * @param status 조회할 상태
     * @param limit 최대 조회 건수
     * @return 실패 이벤트 목록
     */
    List<FailedEvent> findByStatusOrderByCreatedAtAsc(FailedEvent.Status status, int limit);
}
