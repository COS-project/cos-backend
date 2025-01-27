package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.common.Image;
import com.cos.cercat.domain.user.Ownable;
import com.cos.cercat.domain.user.User;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@SuperBuilder
public class Post implements Ownable {

    private Long id;
    private User writer;
    private PostType type;
    private Certificate certificate;
    private PostContent postContent;
    private List<Image> postImages;
    private DateTime dateTime;
    private int commentCount;

    public static Post create(User user, Certificate certificate, NewPost newPost, List<Image> images) {
        return Post.builder()
                .writer(user)
                .type(newPost.postType())
                .certificate(certificate)
                .postContent(new PostContent(newPost.content().title(), newPost.content().content()))
                .postImages(images)
                .dateTime(DateTime.init())
                .commentCount(0)
                .build();

    }

    public Post update(PostContent postContent, List<Image> postImages) {
        this.postContent = postContent;
        this.postImages = postImages;
        return this;
    }

    @Override
    public User getOwner() {
        return writer;
    }
}
