package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostReader {

    private final ReadPostRepository postRepository;

    public Post read(TargetPost targetPost) {
        return postRepository.find(targetPost);
    }

    public PostWithComments readDetail(TargetPost targetPost) {
        PostWithComments postWithComments = postRepository.findDetail(targetPost);
        return postWithComments.organizeChildComments();
    }

    public List<Post> readTop3TipPosts(Certificate certificate) {
        return postRepository.findTop3TipPosts(certificate);
    }

    public SliceResult<Post> readMyPosts(User user, PostType postType, Cursor cursor) {
        return switch (postType) {
            case COMMENTARY -> postRepository.findMyCommentaryPosts(user, cursor);
            case NORMAL -> postRepository.findMyNormalPosts(user, cursor);
            case TIP -> postRepository.findMyTipPosts(user, cursor);
        };
    }
}
