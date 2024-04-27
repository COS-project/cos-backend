package com.cos.cercat.post;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.user.Ownable;
import com.cos.cercat.user.User;
import lombok.Getter;

@Getter
public class Post implements Ownable {

    private final Long id;
    private final User user;
    private final Certificate certificate;
    private final PostContent postContent;
    private final PostStatus postStatus;
    private final DateTime dateTime;

    public Post(Long id, User user, Certificate certificate, PostContent postContent, PostStatus postStatus, DateTime dateTime) {
        this.id = id;
        this.user = user;
        this.certificate = certificate;
        this.postContent = postContent;
        this.postStatus = postStatus;
        this.dateTime = dateTime;
    }

    public void like() {
        postStatus.likeCountUp();
    }

    public void unLike() {
        postStatus.likeCountDown();
    }

    public boolean isOwner(User user) {
        return this.user.getId().equals(user.getId());
    }

    public void update(PostContent postContent) {
        this.postContent.update(postContent);
    }
}
