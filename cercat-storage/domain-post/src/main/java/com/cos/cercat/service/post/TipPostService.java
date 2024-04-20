package com.cos.cercat.service.post;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.post.TipPostEntity;
import com.cos.cercat.entity.Image;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.post.RecommendTagEntity;
import com.cos.cercat.repository.post.TipPostJpaRepository;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
public class TipPostService {

    private final TipPostJpaRepository tipPostJpaRepository;

    public void createTipPost(TipPostEntity tipPost) {
        log.info("userEntity - {}, certificateEntity - {} 꿀팁 게시글 생성",
                tipPost.getUserEntity().getEmail(), tipPost.getCertificateEntity().getCertificateName());
        tipPostJpaRepository.save(tipPost);
    }

    public TipPostEntity getTipPost(Long postId) {
        return tipPostJpaRepository.findById(postId).orElseThrow(() ->
                new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    public Slice<TipPostEntity> getMyTipPosts(UserEntity userEntity, Pageable pageable) {
        return tipPostJpaRepository.findTipPostsByUserEntity(userEntity, pageable);
    }

    public List<TipPostEntity> getTop3TipPosts(CertificateEntity certificateEntity) {
        return tipPostJpaRepository.findTop3ByCertificateEntityOrderByLikeCountDesc(certificateEntity);
    }

    public void updateTipPost(Long postId,
                              String title,
                              String content,
                              Set<RecommendTagEntity> newTags,
                              List<Image> images,
                              UserEntity userEntity
    ) {
        TipPostEntity tipPost = getTipPost(postId);

        if (tipPost.isAuthorized(userEntity)) {
            tipPost.updatePostInfo(title, content, images);
            tipPost.updateRecommendTags(newTags);
            return;
        }

        throw new CustomException(ErrorCode.NO_PERMISSION_ERROR);
    }
}
