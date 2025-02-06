package com.cos.cercat.messaging.postsearch.debezium;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.GenericTypeResolver;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractSimpleEventHandler<T> {

    protected final Map<DebeziumEvent.DebeziumEventPayloadOperation, BiConsumer<T, T>> actions = new ConcurrentHashMap<>();

    private final ObjectMapper mapper;

    public abstract void initActions();

    @SuppressWarnings("unchecked")
    public void process(DebeziumEvent event) {
        DebeziumEvent.DebeziumEventPayload payload = event.getPayload();

        DebeziumEvent.DebeziumEventPayloadOperation operation = payload.getOperation();
        Map<String, Object> payloadBefore = payload.getBefore();
        Map<String, Object> payloadAfter = payload.getAfter();


        Class<T> entityClass = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), AbstractSimpleEventHandler.class);

        if (Objects.isNull(entityClass)) {
            throw new IllegalArgumentException("AbstractSimpleEventHandler should have a type a argument");
        }

        T before = mapper.convertValue(payloadBefore, entityClass);
        T after = mapper.convertValue(payloadAfter, entityClass);

        actions.get(operation).accept(before, after);
    }
}
