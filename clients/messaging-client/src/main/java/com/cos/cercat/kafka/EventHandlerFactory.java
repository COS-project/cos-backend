package com.cos.cercat.kafka;

import com.google.common.collect.Maps;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventHandlerFactory {

    private static final String POST = "post";
    private static final String POST_COMMENT = "post_comment";

    private final PostEventHandler postEventHandler;
    private final PostCommentEventHandler postCommentEventHandler;

    private final Map<String, EventHandler> handlers = Maps.newConcurrentMap();

    @PostConstruct
    public void init() {
        handlers.put(POST, postEventHandler);
        handlers.put(POST_COMMENT, postCommentEventHandler);
    }

    public EventHandler getHandler(String topicName) {
        String tableName = substringAfterLast(topicName).toLowerCase();

        return Optional.ofNullable(handlers.get(tableName))
                .orElseThrow(() -> new IllegalArgumentException(topicName + " 토픽에 대한 핸들러가 없습니다."));
    }

     private String substringAfterLast(String input) {
        int lastIndex = input.lastIndexOf(".");
        return input.substring(lastIndex + 1);
    }

}
