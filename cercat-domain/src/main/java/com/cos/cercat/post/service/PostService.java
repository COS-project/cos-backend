package com.cos.cercat.post.service;

import com.cos.cercat.post.domain.Post;
import com.cos.cercat.post.repository.PostRepository;
import com.cos.cercat.exception.CustomException;
import com.cos.cercat.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    public Post getPostWithLock(Long postId) {
        return postRepository.findByIdWithPessimisticLock(postId).orElseThrow(() ->
                new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    public void deletePost(Long postId) {
        log.info("postId - {} 게시글 삭제", postId);
        postRepository.delete(getPost(postId));
    }

    public void deletePostImages(List<String> imagesUrls) {
        postRepository.deleteAllByImageUrl(imagesUrls);
    }
}
