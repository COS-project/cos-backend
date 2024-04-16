package com.cos.cercat.service.post;

import com.cos.cercat.domain.User;
import com.cos.cercat.domain.post.Post;
import com.cos.cercat.dto.UserDTO;
import com.cos.cercat.repository.UserRepository;
import com.cos.cercat.repository.post.PostRepository;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    public Post getPostWithLock(Long postId) {
        return postRepository.findByIdWithPessimisticLock(postId).orElseThrow(() ->
                new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    public void deletePost(Long postId, Long userId) {
        Post post = getPost(postId);
        User requestUser = getUser(userId);

        if (!post.isAuthorized(requestUser)) throw new CustomException(ErrorCode.NO_PERMISSION_ERROR);

        postRepository.delete(post);
        log.info("postId - {} 게시글 삭제", postId);
    }

    public void deletePostImages(List<String> imagesUrls) {
        postRepository.deleteAllByImageUrl(imagesUrls);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
    }
}
