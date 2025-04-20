package com.cos.cercat.alarm.infrastructure.redis.listener;

import com.cos.cercat.domain.common.event.inbox.InboxEventManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.PendingMessage;
import org.springframework.data.redis.connection.stream.PendingMessages;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AlarmEventDeadLetterRedisStreamProducer {

    private final StringRedisTemplate redisTemplate;
    private StreamOperations<String, String, String> streamOps;

    @Value("${redis.key-prefix.dead-letter}")
    private String dlqPrefix;

    @Value("#{'${redis.key-prefix.alarm-events}'.split(',')}")
    private List<String> eventTypes;

    @PostConstruct
    public void init() {
        streamOps = redisTemplate.opsForStream();

        // 스트림 초기화를 병렬로 처리하고 결과 로깅
        List<String> initializedStreams = eventTypes.parallelStream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(this::createDlqStreamIfAbsent)
                .filter(stream -> stream != null)
                .collect(Collectors.toList());

        if (!initializedStreams.isEmpty()) {
            log.info("DLQ 스트림 초기화 완료 - {}", initializedStreams);
        }
    }

    public int moveToDlq(String sourceStream, List<RecordId> deadLetterIds, String group, String reason) {
        if (deadLetterIds == null || deadLetterIds.isEmpty()) {
            return 0;
        }

        String dlqKey = getDlqKey(sourceStream);
        int successCount = 0;

        // 메시지 조회를 최적화하기 위해 한 번에 모든 메시지 조회
        Map<String, MapRecord<String, String, String>> messageMap = retrieveMessages(sourceStream, deadLetterIds);
        if (messageMap.isEmpty()) {
            log.warn("DLQ로 이동할 메시지를 찾을 수 없음 - stream={}, ids={}", sourceStream, deadLetterIds);
            return 0;
        }

        // 배달 횟수 정보를 한 번에 조회
        Map<String, Long> deliveryCountMap = getDeliveryCountBatch(sourceStream, group, deadLetterIds);

        for (RecordId id : deadLetterIds) {
            try {
                String idValue = id.getValue();
                MapRecord<String, String, String> original = messageMap.get(idValue);

                if (original == null) {
                    continue;
                }

                // DLQ로 이동
                Map<String, String> values = new HashMap<>(original.getValue());

                // DLQ 메타데이터 추가
                values.put("originalStream", sourceStream);
                values.put("originalId", idValue);
                values.put("deliveryCount", String.valueOf(deliveryCountMap.getOrDefault(idValue, -1L)));
                values.put("movedToDlqAt", String.valueOf(System.currentTimeMillis()));
                values.put("failureReason", reason);

                // DLQ에 추가
                RecordId dlqRecordId = streamOps.add(dlqKey, values);

                // 원본 메시지 승인 처리
                streamOps.acknowledge(sourceStream, group, id);

                log.warn("메시지를 DLQ로 이동 - sourceStream={}, sourceId={}, dlqStream={}, dlqId={}, deliveryCount={}",
                        sourceStream, idValue, dlqKey, dlqRecordId.getValue(), deliveryCountMap.getOrDefault(idValue, -1L));

                successCount++;
            } catch (Exception ex) {
                log.error("DLQ로 메시지 이동 중 오류 발생 - stream={}, id={}", sourceStream, id.getValue(), ex);
            }
        }

        return successCount;
    }

    private Map<String, MapRecord<String, String, String>> retrieveMessages(String sourceStream, List<RecordId> ids) {
        Map<String, MapRecord<String, String, String>> messageMap = new HashMap<>();

        try {
            // 최적화: 메시지 범위 조회를 여러 번의 단일 조회 대신 하나의 범위 조회로 처리
            String minId = ids.stream().map(RecordId::getValue).min(String::compareTo).orElse("");
            String maxId = ids.stream().map(RecordId::getValue).max(String::compareTo).orElse("");

            if (!minId.isEmpty() && !maxId.isEmpty()) {
                List<MapRecord<String, String, String>> messages =
                        streamOps.range(sourceStream, Range.closed(minId, maxId));

                if (messages != null) {
                    // ID별로 메시지 매핑
                    for (MapRecord<String, String, String> record : messages) {
                        String recordId = record.getId().getValue();
                        if (ids.stream().anyMatch(id -> id.getValue().equals(recordId))) {
                            messageMap.put(recordId, record);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            log.error("메시지 조회 중 오류 발생 - stream={}", sourceStream, ex);
        }

        return messageMap;
    }

    private Map<String, Long> getDeliveryCountBatch(String sourceStream, String group, List<RecordId> ids) {
        Map<String, Long> deliveryCountMap = new HashMap<>();

        try {
            String minId = ids.stream().map(RecordId::getValue).min(String::compareTo).orElse("");
            String maxId = ids.stream().map(RecordId::getValue).max(String::compareTo).orElse("");

            if (!minId.isEmpty() && !maxId.isEmpty()) {
                // 한 번의 요청으로 모든 ID의 pending 정보 조회
                PendingMessages pendingMessages = streamOps.pending(sourceStream, group,
                        Range.closed(minId, maxId), ids.size());

                for (PendingMessage pendingMessage : pendingMessages) {
                    String id = pendingMessage.getId().getValue();
                    deliveryCountMap.put(id, pendingMessage.getTotalDeliveryCount());
                }
            }
        } catch (Exception ex) {
            log.error("배달 횟수 조회 실패 - stream={}", sourceStream, ex);
        }

        return deliveryCountMap;
    }

    private String createDlqStreamIfAbsent(String type) {
        String dlqKey = dlqPrefix + ":event:" + type;
        try {
            boolean exists = redisTemplate.hasKey(dlqKey);
            if (!exists) {
                Map<String, String> init = new HashMap<>();
                init.put("init", "true");
                streamOps.add(dlqKey, init);
                log.info("DLQ 스트림 생성 – key={}", dlqKey);
                return dlqKey;
            }
        } catch (Exception ex) {
            log.warn("DLQ 스트림 초기화 중 오류 발생 - key={}", dlqKey, ex);
        }
        return null;
    }

    private String getDlqKey(String sourceStream) {
        return dlqPrefix + ":" + sourceStream;
    }
}
