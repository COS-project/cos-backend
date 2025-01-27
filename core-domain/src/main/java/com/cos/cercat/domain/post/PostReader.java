package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.post.exception.PostNotFoundException;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostReader {

    private final ReadPostRepository postRepository;
    private final PostCommentReader postCommentReader;

    public Post read(TargetPost targetPost) {
        return postRepository.find(targetPost).orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }

    public PostWithComments readDetail(TargetPost targetPost) {
        Post post = read(targetPost);
        List<PostComment> comments = postCommentReader.readByPost(targetPost);
        return PostWithComments.of(post, comments);
    }

    public List<Post> readTop3TipPosts(Certificate certificate) {
        return postRepository.findTop3TipPosts(certificate);
    }

    public SliceResult<Post> readMyPosts(User user, PostType postType, Cursor cursor) {
        return postRepository.findMyPosts(user, cursor, postType);
    }
}
