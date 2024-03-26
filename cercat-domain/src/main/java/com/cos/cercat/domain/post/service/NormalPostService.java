package com.cos.cercat.domain.post.service;

import com.cos.cercat.domain.common.domain.Image;
import com.cos.cercat.domain.post.domain.NormalPost;
import com.cos.cercat.domain.post.repository.NormalPostRepository;
import com.cos.cercat.domain.user.domain.User;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Slf4j
@Service
public class NormalPostService {
    private final NormalPostRepository normalPostRepository;

    public void createNormalPost(NormalPost normalPost) {
        log.info("user - {}, certificate - {} 자유 게시글 생성",
                normalPost.getUser().getEmail(), normalPost.getCertificate().getCertificateName());
        normalPostRepository.save(normalPost);

    }

    public NormalPost getNormalPost(Long postId) {
        return normalPostRepository.findById(postId).orElseThrow(() ->
                new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    public Slice<NormalPost> getMyNormalPosts(User user, Pageable pageable) {
        return normalPostRepository.findNormalPostsByUser(user, pageable);
    }

    public void updateNormalPost(Long postId,
                                 String title,
                                 String content,
                                 List<Image> images,
                                 User user
    ) {
        NormalPost normalPost = getNormalPost(postId);

        if (normalPost.isAuthorized(user)) {
            normalPost.updatePostInfo(title, content, images);
            return;
        }

        throw new CustomException(ErrorCode.NO_PERMISSION_ERROR);
    }

}
