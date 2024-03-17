package com.cos.cercat.post.app;

import com.cos.cercat.post.domain.NormalPost;
import com.cos.cercat.post.dto.request.PostSearchCond;
import com.cos.cercat.post.repository.NormalPostRepository;
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

@RequiredArgsConstructor
@Slf4j
@Service
public class NormalPostService {
    private final NormalPostRepository normalPostRepository;

    public void createNormalPost(NormalPost normalPost) {
        log.info("user - {}, certificate - {} 꿀팁 게시글 생성",
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
