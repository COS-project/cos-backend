package com.cos.cercat.post.app;

import com.cos.cercat.post.domain.RecommendTag;
import com.cos.cercat.post.domain.TipPost;
import com.cos.cercat.post.repository.TipPostRepository;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.global.common.Image;
import com.cos.cercat.global.exception.CustomException;
import com.cos.cercat.global.exception.ErrorCode;
import com.cos.cercat.user.domain.User;
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
