package com.cos.cercat.post.dto.request;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.common.domain.Image;
import com.cos.cercat.mockExam.domain.Question;
import com.cos.cercat.post.domain.CommentaryPost;
import com.cos.cercat.post.domain.NormalPost;
import com.cos.cercat.post.domain.PostType;
import com.cos.cercat.post.domain.TipPost;
import com.cos.cercat.post.dto.RecommendTagDTO;
import com.cos.cercat.user.domain.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record PostCreateRequest(
        String title,
        String content,
        Set<RecommendTagDTO> tags,
        Integer examYear,
        Integer round,
        Integer questionSequence
) {
    public CommentaryPost toCommentaryPost(List<Image> images,
                                           Certificate certificate,
                                           User user,
                                           Question question) {
        return new CommentaryPost(
                title,
                content,
                user,
                certificate,
                PostType.COMMENTARY,
                images,
                question
        );
    }

    public TipPost toTipPost(List<Image> images,
                             Certificate certificate,
                             User user) {
        return new TipPost(
                title,
                content,
                user,
                certificate,
                PostType.TIP,
                images,
                tags.stream()
                        .map(RecommendTagDTO::toEntity)
                        .collect(Collectors.toSet())
        );
    }

    public NormalPost toNormalPost(List<Image> images,
                                   Certificate certificate,
                                   User user) {
        return new NormalPost(
                title,
                content,
                user,
                certificate,
                PostType.NORMAL,
                images
        );
    }
}
