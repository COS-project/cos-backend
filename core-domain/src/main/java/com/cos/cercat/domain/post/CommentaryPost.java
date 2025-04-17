package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.common.Image;
import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.user.User;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CommentaryPost extends Post {

    private Question question;

    public Post update(PostContent postContent, Question question, List<Image> images) {
        super.update(postContent, images);
        this.question = question;
        return this;
    }

    public static CommentaryPost create(User user, Certificate certificate, NewPost newPost, List<Image> images, Question question) {
        return CommentaryPost.builder()
                .type(PostType.COMMENTARY)
                .writer(user)
                .certificate(certificate)
                .postContent(newPost.content())
                .question(question)
                .postImages(images)
                .commentCount(0)
                .dateTime(DateTime.init())
                .build();
    }

}
