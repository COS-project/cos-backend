package com.cos.cercat.kafka.debezium;

import com.cos.cercat.domain.search.PostForSearchAppender;
import com.cos.cercat.domain.search.PostForSearchRemover;
import com.cos.cercat.domain.search.SearchPostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import static com.cos.cercat.kafka.debezium.DebeziumEvent.*;

@Component
public class PostCommentEventHandler extends AbstractSimpleEventHandler<PostCommentDebeziumDTO> implements EventHandler{

    private final PostForSearchAppender postForSearchAppender;
    private final PostForSearchRemover postForSearchRemover;

    public PostCommentEventHandler(ObjectMapper mapper,
                                   PostForSearchAppender postForSearchAppender,
                                   PostForSearchRemover postForSearchRemover) {
        super(mapper);
        this.postForSearchAppender = postForSearchAppender;
        this.postForSearchRemover = postForSearchRemover;
    }

    @PostConstruct
    public void init() {
        initActions();
    }

    @Override
    public void initActions() {
        actions.put(DebeziumEventPayloadOperation.CREATE, (before, after) -> postForSearchAppender.appendComment(after.toDomain()));
        actions.put(DebeziumEventPayloadOperation.DELETE, (before, after) -> postForSearchRemover.removeComment(before.toDomain()));
    }
}
