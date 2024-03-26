package com.cos.cercat.service.post;

import com.cos.cercat.entity.Image;
import com.cos.cercat.domain.Certificate;
import com.cos.cercat.domain.User;
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
        log.info("user - {}, certificate - {} 꿀팁 게시글 생성",
                tipPost.getUser().getEmail(), tipPost.getCertificate().getCertificateName());
        tipPostRepository.save(tipPost);
    }

    public TipPost getTipPost(Long postId) {
        return tipPostRepository.findById(postId).orElseThrow(() ->
                new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    public Slice<TipPost> getMyTipPosts(User user, Pageable pageable) {
        return tipPostRepository.findTipPostsByUser(user, pageable);
    }

    public List<TipPost> getTop3TipPosts(Certificate certificate) {
        return tipPostRepository.findTop3ByCertificateOrderByLikeCountDesc(certificate);
    }

    public void updateTipPost(Long postId,
                              String title,
                              String content,
                              Set<RecommendTag> newTags,
                              List<Image> images,
                              User user
    ) {
        TipPost tipPost = getTipPost(postId);

        if (tipPost.isAuthorized(user)) {
            tipPost.updatePostInfo(title, content, images);
            tipPost.updateRecommendTags(newTags);
            return;
        }

        throw new CustomException(ErrorCode.NO_PERMISSION_ERROR);
    }
}
