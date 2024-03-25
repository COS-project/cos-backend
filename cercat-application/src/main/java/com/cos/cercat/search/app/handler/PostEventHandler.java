package com.cos.cercat.search.app.handler;

import com.cos.cercat.search.dto.PostDebeziumDTO;
import com.cos.cercat.search.service.PostSearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.cos.cercat.search.app.dto.DebeziumEvent.*;

@Component
@Slf4j
public class PostEventHandler extends AbstractSimpleEventHandler<PostDebeziumDTO> implements EventHandler{

    private final PostSearchService postSearchService;

    public PostEventHandler(ObjectMapper mapper, PostSearchService postSearchService) {
        super(mapper);
        this.postSearchService = postSearchService;

    }

    @PostConstruct
    public void init() {
        initActions();
    }

    @Override
    public void initActions() {
        actions.put(DebeziumEventPayloadOperation.CREATE, (before, after) -> postSearchService.savePost(after));
        actions.put(DebeziumEventPayloadOperation.UPDATE, (before, after) -> postSearchService.updatePost(after));
        actions.put(DebeziumEventPayloadOperation.DELETE, (before, after) -> postSearchService.delete(before));
    }
}
