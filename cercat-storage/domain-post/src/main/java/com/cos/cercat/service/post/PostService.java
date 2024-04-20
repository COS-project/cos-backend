package com.cos.cercat.service.post;

import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.post.PostEntity;
import com.cos.cercat.repository.UserJpaRepository;
import com.cos.cercat.repository.post.PostJpaRepository;
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

    private final PostJpaRepository postJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public PostEntity getPost(Long postId) {
        return postJpaRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    public PostEntity getPostWithLock(Long postId) {
        return postJpaRepository.findByIdWithPessimisticLock(postId).orElseThrow(() ->
                new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    public void deletePost(Long postId, Long userId) {
        PostEntity postEntity = getPost(postId);
        UserEntity requestUserEntity = getUser(userId);

        if (!postEntity.isAuthorized(requestUserEntity)) throw new CustomException(ErrorCode.NO_PERMISSION_ERROR);

        postJpaRepository.delete(postEntity);
        log.info("postId - {} 게시글 삭제", postId);
    }

    public void deletePostImages(List<String> imagesUrls) {
        postJpaRepository.deleteAllByImageUrl(imagesUrls);
    }

    private UserEntity getUser(Long userId) {
        return userJpaRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
    }
}
