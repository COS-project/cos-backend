package com.cos.cercat.apis.post.dto.request;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.QuestionEntity;
import com.cos.cercat.apis.post.dto.RecommendTagDTO;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.mockexam.MockExamSession;
import com.cos.cercat.domain.post.*;
import com.cos.cercat.domain.post.CommentaryPostEntity;
import com.cos.cercat.domain.post.NormalPostEntity;
import com.cos.cercat.entity.Image;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record PostCreateRequest(
        String title,
        String content,
        Set<RecommendTag> tags,
        Integer examYear,
        Integer round,
        Integer questionSequence
) {

    public PostContent toContent(PostType postType, List<String> imageUrls) {
        return new PostContent(title, content, postType, imageUrls);
    }

    public MockExamSession toMockExamSession() {
        return new MockExamSession(examYear, round);
    }

    public CommentaryPostEntity toCommentaryPost(List<Image> images,
                                                 CertificateEntity certificateEntity,
                                                 UserEntity userEntity,
                                                 QuestionEntity questionEntity) {
        return new CommentaryPostEntity(
                title,
                content,
                userEntity,
                certificateEntity,
                PostType.COMMENTARY,
                images,
                questionEntity
        );
    }

    public TipPostEntity toTipPost(List<Image> images,
                                   CertificateEntity certificateEntity,
                                   UserEntity userEntity) {
        return new TipPostEntity(
                title,
                content,
                userEntity,
                certificateEntity,
                PostType.TIP,
                images,
                tags.stream()
                        .map(recommendTag -> new RecommendTagDTO(recommendTag.tagType(), recommendTag.tagName()).toEntity())
                        .collect(Collectors.toSet())
        );
    }

    public NormalPostEntity toNormalPost(List<Image> images,
                                         CertificateEntity certificateEntity,
                                         UserEntity userEntity) {
        return new NormalPostEntity(
                title,
                content,
                userEntity,
                certificateEntity,
                PostType.NORMAL,
                images
        );
    }
}
