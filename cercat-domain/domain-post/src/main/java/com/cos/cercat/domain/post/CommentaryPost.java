package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.user.User;
import lombok.Getter;

@Getter
public class CommentaryPost extends Post {

    private Question question;

    public CommentaryPost(Long id,
                          User user,
                          Certificate certificate,
                          PostContent postContent,
                          PostStatus postStatus,
                          Question question,
                          DateTime dateTime) {
        super(id, user, certificate, postContent, postStatus, dateTime);
        this.question = question;
    }

    public void update(PostContent postContent, Question question) {
        super.update(postContent);
        this.question = question;
    }

}
