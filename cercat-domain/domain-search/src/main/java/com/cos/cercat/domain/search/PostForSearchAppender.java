package com.cos.cercat.domain.search;

import com.cos.cercat.domain.post.TargetPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostForSearchAppender {

    private final PostForSearchRepository postForSearchRepository;

    public void append(PostForSearch post) {
        postForSearchRepository.save(post);
    }

    public void appendComment(PostCommentForSearch postComment) {
        PostForSearch post = postForSearchRepository.find(TargetPost.from(postComment.postId()));
        post.addComment(postComment);
        postForSearchRepository.save(post);
    }
}
