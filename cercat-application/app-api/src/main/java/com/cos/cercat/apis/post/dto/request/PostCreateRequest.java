package com.cos.cercat.apis.post.dto.request;

import com.cos.cercat.domain.Certificate;
import com.cos.cercat.domain.Question;
import com.cos.cercat.apis.post.dto.RecommendTagDTO;
import com.cos.cercat.domain.User;
import com.cos.cercat.domain.post.CommentaryPost;
import com.cos.cercat.domain.post.NormalPost;
import com.cos.cercat.domain.post.PostType;
import com.cos.cercat.domain.post.TipPost;
import com.cos.cercat.entity.Image;

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
