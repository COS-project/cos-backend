package com.cos.cercat.infra.debezium;

import com.cos.cercat.domain.search.PostForSearchAppender;
import com.cos.cercat.domain.search.PostForSearchRemover;
import com.cos.cercat.domain.search.PostForSearchUpdator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.cos.cercat.infra.debezium.DebeziumEvent.*;

@Component
@Slf4j
public class PostEventHandler extends AbstractSimpleEventHandler<PostDebeziumDTO> implements EventHandler{

    private final PostForSearchAppender postForSearchAppender;
    private final PostForSearchUpdator postForSearchUpdator;
    private final PostForSearchRemover postForSearchRemover;

    public PostEventHandler(ObjectMapper mapper,
                            PostForSearchAppender postForSearchAppender,
                            PostForSearchUpdator postForSearchUpdator,
                            PostForSearchRemover postForSearchRemover) {
        super(mapper);
        this.postForSearchAppender = postForSearchAppender;
        this.postForSearchUpdator = postForSearchUpdator;
        this.postForSearchRemover = postForSearchRemover;
    }

    @PostConstruct
    public void init() {
        initActions();
    }

    @Override
    public void initActions() {
        actions.put(DebeziumEventPayloadOperation.CREATE, (before, after) -> postForSearchAppender.append(after.toDomain()));
        actions.put(DebeziumEventPayloadOperation.UPDATE, (before, after) -> postForSearchUpdator.update(after.toDomain()));
        actions.put(DebeziumEventPayloadOperation.DELETE, (before, after) -> postForSearchRemover.remove(before.toDomain()));
    }
}
