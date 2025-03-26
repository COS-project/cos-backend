package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.common.Image;
import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.mockexam.QuestionReader;
import com.cos.cercat.domain.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentaryPostAppendStrategy implements PostAppendStrategy {

    private final QuestionReader questionReader;
    private final CreatePostRepository postRepository;

    @Override
    public Boolean supports(PostType postType) {
        return postType == PostType.COMMENTARY;
    }

    @Override
    public PostId append(User user, Certificate certificate, NewPost newPost, List<Image> images) {
        Question question = questionReader.read(certificate, newPost.mockExamSession(),
                newPost.questionSequence());
        CommentaryPost.create(user, certificate, newPost, images, question);
        return postRepository.save(CommentaryPost.create(user, certificate, newPost, images, question));
    }
}
