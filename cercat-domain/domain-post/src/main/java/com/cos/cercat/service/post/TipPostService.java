package com.cos.cercat.service.post;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.entity.Image;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.post.RecommendTag;
import com.cos.cercat.domain.post.TipPost;
import com.cos.cercat.repository.post.TipPostRepository;
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

    private final TipPostRepository tipPostRepository;

    public void createTipPost(TipPost tipPost) {
        log.info("userEntity - {}, certificateEntity - {} 꿀팁 게시글 생성",
                tipPost.getUserEntity().getEmail(), tipPost.getCertificateEntity().getCertificateName());
        tipPostRepository.save(tipPost);
    }

    public TipPost getTipPost(Long postId) {
        return tipPostRepository.findById(postId).orElseThrow(() ->
                new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    public Slice<TipPost> getMyTipPosts(UserEntity userEntity, Pageable pageable) {
        return tipPostRepository.findTipPostsByUserEntity(userEntity, pageable);
    }

    public List<TipPost> getTop3TipPosts(CertificateEntity certificateEntity) {
        return tipPostRepository.findTop3ByCertificateEntityOrderByLikeCountDesc(certificateEntity);
    }

    public void updateTipPost(Long postId,
                              String title,
                              String content,
                              Set<RecommendTag> newTags,
                              List<Image> images,
                              UserEntity userEntity
    ) {
        TipPost tipPost = getTipPost(postId);

        if (tipPost.isAuthorized(userEntity)) {
            tipPost.updatePostInfo(title, content, images);
            tipPost.updateRecommendTags(newTags);
            return;
        }

        throw new CustomException(ErrorCode.NO_PERMISSION_ERROR);
    }
}
