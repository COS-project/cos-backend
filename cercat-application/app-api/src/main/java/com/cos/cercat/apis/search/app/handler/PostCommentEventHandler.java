package com.cos.cercat.apis.search.app.handler;

import com.cos.cercat.dto.PostCommentDebeziumDTO;
import com.cos.cercat.service.PostSearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import static com.cos.cercat.apis.search.app.dto.DebeziumEvent.*;

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
        actions.put(DebeziumEventPayloadOperation.DELETE, (before, after) -> postSearchService.deleteComment(before));
    }
}
