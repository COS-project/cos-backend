package com.cos.cercat.post.app.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
public class DebeziumEvent {

    private Map<String, Object> schema;
    private DebeziumEventPayload payload;

    @RequiredArgsConstructor
    public enum DebeziumEventPayloadOperation {
        CREATE("c"), UPDATE("u"), DELETE("r");
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
    }
}
