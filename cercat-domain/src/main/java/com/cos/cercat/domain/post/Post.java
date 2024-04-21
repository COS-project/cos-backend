package com.cos.cercat.domain.post;

import com.cos.cercat.domain.user.User;
import lombok.Getter;


@Getter
public class Post {

    private final Long id;
    private final User user;
    private final PostContent postContent;
    private final PostStatus postStatus;
    private final DateTime dateTime;

    public Post(Long id, User user, PostContent postContent, PostStatus postStatus, DateTime dateTime) {
        this.id = id;
        this.user = user;
        this.postContent = postContent;
        this.postStatus = postStatus;
        this.dateTime = dateTime;
    }

}
