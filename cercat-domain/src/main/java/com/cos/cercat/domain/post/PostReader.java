package com.cos.cercat.domain.post;

import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
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

    public PostComment readComment(TargetComment targetComment) {
        return postRepository.find(targetComment);
    }

    public PostComment readCommentToLike(TargetComment targetComment) {
        return postRepository.findCommentWithLock(targetComment);
    }

    public SliceResult<PostComment> readComment(TargetUser targetUser, Cursor cursor) {
        return postRepository.findComment(targetUser, cursor);
    }

    public List<Post> readTop3TipPosts(TargetCertificate targetCertificate) {
        return postRepository.findTop3TipPosts(targetCertificate);
    }

    public SliceResult<Post> readMyPosts(TargetUser targetUser, PostType postType, Cursor cursor) {
        return switch (postType) {
            case COMMENTARY -> postRepository.findMyCommentaryPosts(targetUser, cursor);
            case NORMAL -> postRepository.findMyNormalPosts(targetUser, cursor);
            case TIP -> postRepository.findMyTipPosts(targetUser, cursor);
        };
    }
}
