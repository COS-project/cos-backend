package com.cos.cercat.board.app;

import com.cos.cercat.board.domain.NormalPost;
import com.cos.cercat.board.domain.TipPost;
import com.cos.cercat.board.repository.TipPostRepository;
import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.global.entity.Image;
import com.cos.cercat.global.exception.CustomException;
import com.cos.cercat.global.exception.ErrorCode;
import com.cos.cercat.user.domain.User;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cos.cercat.board.domain.QNormalPost.normalPost;

@RequiredArgsConstructor
@Service
@Slf4j
public class TipPostService {

    private final TipPostRepository tipPostRepository;

    public void createTipPost(String title,
                              String content,
                              List<Image> images,
                              Certificate certificate,
                              User user) {
        TipPost tipPost = TipPost.of(title, content, user, certificate);
        tipPost.addAllPostImages(images);

        tipPostRepository.save(tipPost);
    }

    public Slice<TipPost> searchTipPosts(Pageable pageable, Certificate certificate, String keyword) {
        return tipPostRepository.searchPosts(pageable, certificate, keyword);
    }

    public TipPost getTipPost(Long postId) {
        return tipPostRepository.findById(postId).orElseThrow(() ->
                new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    public void updateTipPost(Long postId,
                              String title,
                              String content,
                              List<Image> images,
                              User user
    ) {
        TipPost tipPost = getTipPost(postId);

        if (tipPost.isAuthorized(user)) {
            tipPost.updatePostInfo(title, content, images);
            return;
        }

        throw new CustomException(ErrorCode.NO_PERMISSION_ERROR);
    }
}