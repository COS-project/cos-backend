package com.cos.cercat.like.app;

import com.cos.cercat.board.domain.Post;
import com.cos.cercat.like.domain.EmbeddedId.PostLikePK;
import com.cos.cercat.like.domain.PostLike;
import com.cos.cercat.like.repository.PostLikeRepository;
import com.cos.cercat.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostLikeService {


    private final PostLikeRepository postLikeRepository;

    @Transactional
    public void flipPostLike(Post post, User user) {

        PostLikePK postLikePK = PostLikePK.of(user, post);

        if (postLikeRepository.existsById(postLikePK)) {
            postLikeRepository.deleteById(postLikePK);
            post.decreaseLikeCount();
            return;
        }

        postLikeRepository.save(PostLike.from(postLikePK));
        post.increaseLikeCount();
    }

}
