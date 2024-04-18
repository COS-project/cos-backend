package com.cos.cercat.apis.post.dto.request;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.Question;
import com.cos.cercat.apis.post.dto.RecommendTagDTO;
import com.cos.cercat.domain.UserEntity;
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
                                           CertificateEntity certificateEntity,
                                           UserEntity userEntity,
                                           Question question) {
        return new CommentaryPost(
                title,
                content,
                userEntity,
                certificateEntity,
                PostType.COMMENTARY,
                images,
                question
        );
    }

    public TipPost toTipPost(List<Image> images,
                             CertificateEntity certificateEntity,
                             UserEntity userEntity) {
        return new TipPost(
                title,
                content,
                userEntity,
                certificateEntity,
                PostType.TIP,
                images,
                tags.stream()
                        .map(RecommendTagDTO::toEntity)
                        .collect(Collectors.toSet())
        );
    }

    public NormalPost toNormalPost(List<Image> images,
                                   CertificateEntity certificateEntity,
                                   UserEntity userEntity) {
        return new NormalPost(
                title,
                content,
                userEntity,
                certificateEntity,
                PostType.NORMAL,
                images
        );
    }
}
