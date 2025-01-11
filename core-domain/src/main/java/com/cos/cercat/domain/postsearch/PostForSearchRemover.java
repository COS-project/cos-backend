package com.cos.cercat.domain.postsearch;

import com.cos.cercat.domain.post.TargetPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostForSearchRemover {

    private final PostForSearchRepository postForSearchRepository;

    public void remove(PostForSearch post) {
        postForSearchRepository.delete(TargetPost.from(post.getId()));
    }

    public void removeComment(PostCommentForSearch postComment) {
        PostForSearch post = postForSearchRepository.find(TargetPost.from(postComment.postId()));
        post.removeComment(postComment);
        postForSearchRepository.save(post);
    }

}
