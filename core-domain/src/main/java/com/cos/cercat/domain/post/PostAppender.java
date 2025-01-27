package com.cos.cercat.domain.post;

import com.cos.cercat.domain.common.Image;
import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.mockexam.QuestionReader;
import com.cos.cercat.domain.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostAppender {

  private final CreatePostRepository postRepository;
  private final QuestionReader questionReader;

    public TargetPost append(
            User user,
            Certificate certificate,
            NewPost newPost,
            List<Image> images
    ) {
        Post post = switch (newPost.postType()) {
            case COMMENTARY -> createCommentaryPost(user, certificate, newPost, images);
            case NORMAL -> createNormalPost(user, certificate, newPost, images);
            case TIP -> createTipPost(user, certificate, newPost, images);
        };
        return postRepository.save(post);
    }

  private Post createCommentaryPost(
          User user,
          Certificate certificate,
          NewPost newPost,
          List<Image> images
  ) {
      Question question = questionReader.read(certificate, newPost.mockExamSession(),
          newPost.questionSequence());

      return CommentaryPost.create(user, certificate, newPost, images, question);
  }

  private Post createNormalPost(
          User user,
          Certificate certificate,
          NewPost newPost,
          List<Image> images
  ) {
      return Post.create(user, certificate, newPost, images);
  }

  private Post createTipPost(
          User user,
          Certificate certificate,
          NewPost newPost,
          List<Image> images
  ) {
      return TipPost.create(user, certificate, newPost, images);
  }
}
