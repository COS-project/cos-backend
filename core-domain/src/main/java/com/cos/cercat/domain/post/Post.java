package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.common.Image;
import com.cos.cercat.domain.user.Ownable;
import com.cos.cercat.domain.user.User;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class Post implements Ownable {

    private Long id;
    private User user;
    private Certificate certificate;
    private PostContent postContent;
    private PostStatus postStatus;
    private List<Image> postImages;
    private DateTime dateTime;

    public Post(Long id, User user, Certificate certificate, PostContent postContent, PostStatus postStatus, DateTime dateTime) {
        this.id = id;
        this.user = user;
        this.certificate = certificate;
        this.postContent = postContent;
        this.postStatus = postStatus;
        this.dateTime = dateTime;
    }

    public void update(PostContent postContent, List<Image> postImages) {
        this.postContent = postContent;
        this.postImages = postImages;
    }
}
