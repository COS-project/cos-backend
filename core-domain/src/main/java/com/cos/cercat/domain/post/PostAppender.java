package com.cos.cercat.domain.post;

import com.cos.cercat.domain.common.Image;
import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.user.User;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostAppender {

  private final Set<PostAppendStrategy> postCreationStrategies;


    public TargetPost append(
            User user,
            Certificate certificate,
            NewPost newPost,
            List<Image> images
    ) {
        PostAppendStrategy strategy = postCreationStrategies.stream()
                .filter(s -> s.supports(newPost.postType()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 게시글 타입: " + newPost.postType()));

        return strategy.append(user, certificate, newPost, images);
    }
}
