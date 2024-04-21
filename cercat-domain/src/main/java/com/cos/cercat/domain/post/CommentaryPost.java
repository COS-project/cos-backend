package com.cos.cercat.domain.post;

import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.user.User;
import lombok.Getter;

@Getter
public class CommentaryPost extends Post {

    private Question question;

    public CommentaryPost(Long id, User user, PostContent postContent, PostStatus postStatus, Question question, DateTime dateTime) {
        super(id, user, postContent, postStatus, dateTime);
        this.question = question;
    }



}
