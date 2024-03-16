package com.cos.cercat.post.app.search.handler;

import com.cos.cercat.post.app.search.DebeziumEvent;
import com.cos.cercat.post.app.search.dto.PostDebeziumDTO;
import com.cos.cercat.post.app.search.service.PostSearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

    }
}
