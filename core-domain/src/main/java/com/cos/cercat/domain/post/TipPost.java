package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.common.Image;
import com.cos.cercat.domain.user.User;
import java.util.List;
import lombok.Getter;

import java.util.Set;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class TipPost extends Post {

    private Set<RecommendTag> recommendTags;

    public static TipPost create(User user, Certificate certificate, NewPost newPost, List<Image> images) {
        return TipPost.builder()
                .writer(user)
                .type(newPost.postType())
                .certificate(certificate)
                .postContent(new PostContent(newPost.content().title(), newPost.content().content()))
                .postImages(images)
                .dateTime(DateTime.init())
                .commentCount(0)
                .recommendTags(newPost.tags())
                .build();
    }

    public Post update(PostContent postContent, List<Image> images, Set<RecommendTag> recommendTags) {
        super.update(postContent, images);
        this.recommendTags = recommendTags;
        return this;
    }

}
