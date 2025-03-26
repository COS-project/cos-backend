package com.cos.cercat.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostSearcher {

    private final ReadPostRepository postRepository;
}
