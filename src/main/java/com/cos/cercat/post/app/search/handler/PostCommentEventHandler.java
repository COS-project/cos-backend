package com.cos.cercat.post.app.search.handler;

import com.cos.cercat.post.app.search.DebeziumEvent;
import com.cos.cercat.post.app.search.dto.PostCommentDebeziumDTO;
import com.cos.cercat.post.app.search.service.PostSearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import static com.cos.cercat.post.app.search.DebeziumEvent.*;

@Component
public class PostCommentEventHandler extends AbstractSimpleEventHandler<PostCommentDebeziumDTO> implements EventHandler{

    private final PostSearchService postSearchService;

    public PostCommentEventHandler(ObjectMapper mapper, PostSearchService postSearchService) {
        super(mapper);
        this.postSearchService = postSearchService;
    }

    @PostConstruct
    public void init() {
        initActions();
    }

    @Override
    public void initActions() {
        actions.put(DebeziumEventPayloadOperation.CREATE, (before, after) -> postSearchService.saveComment(after));
        actions.put(DebeziumEventPayloadOperation.READ, (before, after) -> postSearchService.saveComment(after));
        actions.put(DebeziumEventPayloadOperation.UPDATE, (before, after) -> postSearchService.saveComment(after));
        actions.put(DebeziumEventPayloadOperation.DELETE, (before, after) -> postSearchService.deleteComment(before));
    }
}
