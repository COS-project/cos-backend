package com.cos.cercat.kafka.debezium;

import com.google.common.collect.Maps;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventHandlerFactory {

    private final PostEventHandler postEventHandler;
    private final PostCommentEventHandler postCommentEventHandler;

    private final Map<String, EventHandler> handlers = Maps.newConcurrentMap();

    @PostConstruct
    public void init() {
        handlers.put("post", postEventHandler);
        handlers.put("post_comment", postCommentEventHandler);
    }

    public EventHandler getHandler(String topicName) {
        String tableName = substringAfterLast(topicName).toLowerCase();

        return Optional.ofNullable(handlers.get(tableName))
                .orElseThrow(() -> new IllegalArgumentException("No handler found for topic: " + topicName));
    }

     private String substringAfterLast(String input) {
        int lastIndex = input.lastIndexOf(".");
        return input.substring(lastIndex + 1);
    }

}
