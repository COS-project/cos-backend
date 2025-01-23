package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.common.Image;
import com.cos.cercat.domain.user.User;
import java.util.List;
import lombok.Getter;

import java.util.Set;

@Getter
public class TipPost extends Post {

    private Set<RecommendTag> recommendTags;

    public TipPost(Long id,
                   User user,
                   Certificate certificate,
                   PostContent postContent,
                   PostStatus postStatus,
                   List<Image> images,
                   DateTime dateTime,
                   Set<RecommendTag> recommendTags) {
        super(id, user, certificate, postContent, postStatus, images, dateTime);
        this.recommendTags = recommendTags;
    }

    public void update(PostContent postContent, Set<RecommendTag> recommendTags, List<Image> images) {
        super.update(postContent, images);
        this.recommendTags = recommendTags;
    }

}
