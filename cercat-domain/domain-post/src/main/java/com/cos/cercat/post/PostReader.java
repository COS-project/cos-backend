package com.cos.cercat.post;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostReader {

    private final ReadPostRepository postRepository;

    public Post readToLike(TargetPost targetPost) {
        return postRepository.findWithLock(targetPost);
    }

    public Post read(TargetPost targetPost) {
        return postRepository.find(targetPost);
    }

    public PostWithComments readDetail(TargetPost targetPost) {
        PostWithComments postWithComments = postRepository.findDetail(targetPost);
        List<PostComment> organizedComments = postWithComments.organizeChildComments();
        return PostWithComments.of(postWithComments.post(), organizedComments);

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
