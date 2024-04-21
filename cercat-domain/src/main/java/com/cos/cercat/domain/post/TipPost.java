package com.cos.cercat.domain.post;

import com.cos.cercat.domain.user.User;
import lombok.Getter;

import java.util.Set;

@Getter
public class TipPost extends Post {

    private Set<RecommendTag> recommendTags;

    public TipPost(Long id, User user, PostContent postContent, PostStatus postStatus, DateTime dateTime, Set<RecommendTag> recommendTags) {
        super(id, user, postContent, postStatus, dateTime);
        this.recommendTags = recommendTags;
    }

}
