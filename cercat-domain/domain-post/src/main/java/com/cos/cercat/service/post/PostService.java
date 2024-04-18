package com.cos.cercat.service.post;

import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.post.Post;
import com.cos.cercat.repository.UserJpaRepository;
import com.cos.cercat.repository.post.PostRepository;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserJpaRepository userJpaRepository;

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    public Post getPostWithLock(Long postId) {
        return postRepository.findByIdWithPessimisticLock(postId).orElseThrow(() ->
                new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    public void deletePost(Long postId, Long userId) {
        Post post = getPost(postId);
        UserEntity requestUserEntity = getUser(userId);

        if (!post.isAuthorized(requestUserEntity)) throw new CustomException(ErrorCode.NO_PERMISSION_ERROR);

        postRepository.delete(post);
        log.info("postId - {} 게시글 삭제", postId);
    }

    public void deletePostImages(List<String> imagesUrls) {
        postRepository.deleteAllByImageUrl(imagesUrls);
    }

    private UserEntity getUser(Long userId) {
        return userJpaRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
    }
}
