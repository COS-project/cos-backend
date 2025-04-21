package com.cos.cercat.alarm.infrastructure.redis.listener;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.stream.MapRecord;
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

        List<String> initializedStreams = eventTypes.stream()
                .map(this::createDlqStreamIfAbsent)
                .filter(Objects::nonNull)
                .toList();

        if (!initializedStreams.isEmpty()) {
            log.info("DLQ 스트림 초기화 완료 - {}", initializedStreams);
        }
    }

    public int publish(String sourceStream, Map<RecordId, Long> deadLetterInfo, String group) {
        if (deadLetterInfo == null || deadLetterInfo.isEmpty()) {
            return 0;
        }

        String dlqKey = getDlqKey(sourceStream);
        int successCount = 0;
        List<RecordId> successfullyMovedIds = new ArrayList<>(); // 배치 ACK를 위한 리스트

        // 이동 대상 ID 리스트 추출
        List<RecordId> deadLetterIds = new ArrayList<>(deadLetterInfo.keySet());

        // 메시지 내용을 한 번에 조회
        Map<String, MapRecord<String, String, String>> messageMap = retrieveMessages(sourceStream, deadLetterIds);
        if (messageMap.isEmpty()) {
            log.warn("DLQ로 이동할 메시지의 원본 내용을 찾을 수 없음 - stream={}, ids={}", sourceStream, deadLetterIds);
            // 원본 메시지 없으면 ACK도 불가하므로 여기서 종료
            return 0;
        }

        for (Map.Entry<RecordId, Long> entry : deadLetterInfo.entrySet()) {
            RecordId id = entry.getKey();
            Long deliveryCount = entry.getValue(); // 전달받은 배달 횟수 사용
            String idValue = id.getValue();

            try {
                MapRecord<String, String, String> original = messageMap.get(idValue);

                // DLQ로 이동
                Map<String, String> values = new HashMap<>(original.getValue());

                // DLQ 메타데이터 추가
                values.put("originalStream", sourceStream);
                values.put("originalId", idValue);
                values.put("deliveryCount", String.valueOf(deliveryCount)); // 전달받은 값 사용
                values.put("movedToDlqAt", String.valueOf(System.currentTimeMillis()));

                // DLQ에 추가
                RecordId dlqRecordId = streamOps.add(dlqKey, values);

                // DLQ 추가 성공 시, ACK 대상 리스트에 추가
                successfullyMovedIds.add(id);

                log.warn("메시지를 DLQ로 이동 준비 완료 (ACK는 배치 처리 예정) - sourceStream={}, sourceId={}, dlqStream={}, dlqId={}, deliveryCount={}",
                        sourceStream, idValue, dlqKey, dlqRecordId.getValue(), deliveryCount);

                successCount++;
            } catch (Exception ex) {
                log.error("DLQ로 메시지 이동 중 개별 오류 발생 - stream={}, id={}", sourceStream, idValue, ex);
                // 실패한 메시지는 successfullyMovedIds에 추가되지 않음
            }
        }

        // 성공적으로 DLQ에 추가된 메시지들만 원본 스트림에서 배치 ACK 처리
        if (!successfullyMovedIds.isEmpty()) {
            try {
                Long ackCount = streamOps.acknowledge(sourceStream, group, successfullyMovedIds.toArray(new RecordId[0]));
                log.info("DLQ 이동 완료 후 원본 메시지 {}개 배치 ACK 처리 완료 - stream={}, group={}", ackCount, sourceStream, group);
            } catch (Exception e) {
                log.error("DLQ 이동 후 배치 ACK 처리 중 오류 발생 - stream={}, group={}, ids={}", sourceStream, group, successfullyMovedIds, e);
                // ACK 실패 시 해당 메시지들은 PENDING 상태로 남아 다음 retry 로직에서 다시 처리될 수 있음 (또는 수동 처리 필요)
            }
        }

        return successCount; // DLQ에 성공적으로 'add'된 횟수 반환
    }

    private Map<String, MapRecord<String, String, String>> retrieveMessages(String sourceStream, List<RecordId> ids) {
        Map<String, MapRecord<String, String, String>> messageMap = new HashMap<>();
        if (ids == null || ids.isEmpty()) {
            return messageMap;
        }

        // ID 값 리스트 생성 (나중에 필터링에 사용)
        List<String> idValues = ids.stream().map(RecordId::getValue).toList();

        try {
            String minId = idValues.stream().min(String::compareTo).orElse("");
            String maxId = idValues.stream().max(String::compareTo).orElse("");

            if (!minId.isEmpty() && !maxId.isEmpty()) {
                List<MapRecord<String, String, String>> messages =
                        streamOps.range(sourceStream, Range.closed(minId, maxId)); // ID 범위로 조회

                if (messages != null) {
                    for (MapRecord<String, String, String> record : messages) {
                        String recordIdValue = record.getId().getValue();
                        messageMap.put(recordIdValue, record);
                    }
                }
            }
        } catch (Exception ex) {
            log.error("메시지 내용 조회 중 오류 발생 - stream={}", sourceStream, ex);
        }

        return messageMap;
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
