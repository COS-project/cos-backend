package com.cos.cercat.service.post;

import com.cos.cercat.entity.Image;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.post.NormalPost;
import com.cos.cercat.repository.post.NormalPostRepository;
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
        log.info("userEntity - {}, certificateEntity - {} 자유 게시글 생성",
                normalPost.getUserEntity().getEmail(), normalPost.getCertificateEntity().getCertificateName());
        normalPostRepository.save(normalPost);

    }

    public NormalPost getNormalPost(Long postId) {
        return normalPostRepository.findById(postId).orElseThrow(() ->
                new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    public Slice<NormalPost> getMyNormalPosts(UserEntity userEntity, Pageable pageable) {
        return normalPostRepository.findNormalPostsByUserEntity(userEntity, pageable);
    }

    public void updateNormalPost(Long postId,
                                 String title,
                                 String content,
                                 List<Image> images,
                                 UserEntity userEntity
    ) {
        NormalPost normalPost = getNormalPost(postId);

        if (normalPost.isAuthorized(userEntity)) {
            normalPost.updatePostInfo(title, content, images);
            return;
        }

        throw new CustomException(ErrorCode.NO_PERMISSION_ERROR);
    }

}
