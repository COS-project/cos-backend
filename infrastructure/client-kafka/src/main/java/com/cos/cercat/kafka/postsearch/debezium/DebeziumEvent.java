package com.cos.cercat.kafka.postsearch.debezium;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
public class DebeziumEvent {

    private Map<String, Object> schema;
    private DebeziumEventPayload payload;

    @RequiredArgsConstructor
    public enum DebeziumEventPayloadOperation {
        CREATE("c"), UPDATE("u"), DELETE("d"), READ("r");
        private final String value;

        @JsonValue
        public String getValue() {
            return value;
        }
    }

    @Data
    public static class DebeziumEventPayload {
        private Map<String, Object> before;
        private Map<String, Object> after;
        private Map<String, Object> source;

        @JsonProperty("op")
        private DebeziumEventPayloadOperation operation;

        @JsonProperty("ts_ms")
        private Date date;
    }
}
