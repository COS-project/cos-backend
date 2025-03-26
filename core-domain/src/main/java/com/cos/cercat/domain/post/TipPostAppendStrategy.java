package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.common.Image;
import com.cos.cercat.domain.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TipPostAppendStrategy implements PostAppendStrategy {

    private final CreatePostRepository postRepository;

    @Override
    public Boolean supports(PostType postType) {
        return postType == PostType.TIP;
    }

    @Override
    public PostId append(User user, Certificate certificate, NewPost newPost, List<Image> images) {
        TipPost tipPost = TipPost.create(user, certificate, newPost, images);
        return postRepository.save(tipPost);
    }
}
