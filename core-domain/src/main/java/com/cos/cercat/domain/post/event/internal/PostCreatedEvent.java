package com.cos.cercat.domain.post.event.internal;


import com.cos.cercat.domain.post.PostId;

public record PostCreatedEvent(
        PostId postId
) {}

