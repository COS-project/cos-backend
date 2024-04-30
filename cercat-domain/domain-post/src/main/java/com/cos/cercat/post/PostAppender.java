package com.cos.cercat.post;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.mockexam.MockExamReader;
import com.cos.cercat.mockexam.Question;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostAppender {

    private final CreatePostRepository postRepository;
    private final MockExamReader mockExamReader;

    public TargetPost append(User user, Certificate certificate, NewPost newPost) {
        return switch (newPost.postType()) {
            case COMMENTARY -> appendCommentaryPost(user, certificate, newPost);
            case NORMAL -> appendNormalPost(user, certificate, newPost);
            case TIP -> appendTipPost(user, certificate, newPost);
        };
    }

    public void appendComment(User user, Post post, CommentContent content) {
        postRepository.saveComment(user, post, content);
    }


    private TargetPost appendCommentaryPost(User user,
                                           Certificate certificate,
                                           NewPost newPost) {
        Question question = mockExamReader.readQuestion(certificate, newPost.mockExamSession(), newPost.questionSequence());
        return postRepository.saveCommentaryPost(user, certificate, newPost.content(), question);
    }

    private TargetPost appendNormalPost(User user,
                                       Certificate certificate,
                                       NewPost newPost) {
        return postRepository.saveNormalPost(user, certificate, newPost.content());
    }

    private TargetPost appendTipPost(User user,
                                    Certificate certificate,
                                    NewPost newPost) {
        return postRepository.saveTipPost(user, certificate, newPost.content(), newPost.tags());
    }
}
