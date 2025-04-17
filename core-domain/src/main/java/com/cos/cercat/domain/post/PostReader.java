package com.cos.cercat.domain.post;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.post.exception.PostNotFoundException;
import com.cos.cercat.domain.searchlog.SearchCond;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostReader {

    private final ReadPostRepository postRepository;
    private final PostCommentReader postCommentReader;
    private final PostCache postCache;

    public SliceResult<Post> read(
            Certificate certificate,
            SearchCond cond,
            Cursor cursor
    ) {
        return postRepository.findPosts(certificate, cond, cursor);
    }

    public SliceResult<Post> readCommentaries(Certificate certificate,
            CommentarySearchCond commentarySearchCond,
            Cursor cursor) {
        return postRepository.findCommentaries(certificate, commentarySearchCond, cursor);
    }

    public Post read(PostId postId) {
        return postRepository.find(postId).orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }

    public Post readForUpdate(PostId postId) {
        return postRepository.findForUpdate(postId).orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }

    public PostWithComments readDetail(PostId postId) {
        Post post = read(postId);
        List<PostComment> comments = postCommentReader.readByPost(postId);
        return PostWithComments.of(post, comments);
    }

    public List<Post> readTop3TipPosts(Certificate certificate) {
        return postCache.getTipPosts(certificate)
                .orElseGet(() -> {
                    List<Post> posts = postRepository.findTop3TipPosts(certificate);
                    postCache.cacheTipPosts(certificate, posts);
                    return posts;
                });
    }

    public SliceResult<Post> readMyPosts(User user, PostType postType, Cursor cursor) {
        return postRepository.findMyPosts(user, cursor, postType);
    }
}
