package com.cos.cercat.board.app;

import com.cos.cercat.board.domain.Post;
import com.cos.cercat.board.repository.PostRepository;
import com.cos.cercat.global.exception.CustomException;
import com.cos.cercat.global.exception.ErrorCode;
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

    public void deletePost(Long postId) {
        postRepository.delete(getPost(postId));
    }

    public void deletePostImages(List<String> imagesUrls) {
        postRepository.deleteAllByImageUrl(imagesUrls);
    }
}
