package com.cos.cercat.repository.post;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.QuestionEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.post.*;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.entity.Image;
import com.cos.cercat.repository.CertificateJpaRepository;
import com.cos.cercat.repository.QuestionJpaRepository;
import com.cos.cercat.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class PostCoreRepository implements PostRepository {

    private final PostJpaRepository postJpaRepository;
    private final NormalPostJpaRepository normalPostJpaRepository;
    private final TipPostJpaRepository tipPostJpaRepository;
    private final CommentaryPostJpaRepository commentaryPostJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final QuestionJpaRepository questionJpaRepository;
    private final CertificateJpaRepository certificateJpaRepository;
    private final PostImageJpaRepository postImageJpaRepository;
    private final RecommendTagJpaRepository recommendTagJpaRepository;

    @Override
    public TargetPost saveCommentaryPost(TargetUser targetUser,
                                   TargetCertificate targetCertificate,
                                   PostContent postContent,
                                   Question question) {

        UserEntity userEntity = userJpaRepository.getReferenceById(targetUser.userId());
        QuestionEntity questionEntity = questionJpaRepository.getReferenceById(question.id());
        CertificateEntity certificateEntity = certificateJpaRepository.getReferenceById(targetCertificate.certificateId());


        CommentaryPostEntity commentaryPostEntity = new CommentaryPostEntity(
                postContent.title(),
                postContent.content(),
                userEntity,
                certificateEntity,
                postContent.postType(),
                questionEntity
        );

        CommentaryPostEntity savedPost = commentaryPostJpaRepository.save(commentaryPostEntity);


        List<PostImageEntity> postImageEntities = postContent.imageUrls().stream()
                .map(imageUrl -> PostImageEntity.of(savedPost, Image.from(imageUrl)))
                .toList();

        postImageJpaRepository.saveAll(postImageEntities);
        return TargetPost.from(savedPost.getId());
    }

    @Override
    public TargetPost saveNormalPost(TargetUser targetUser, TargetCertificate targetCertificate, PostContent postContent) {
        UserEntity userEntity = userJpaRepository.getReferenceById(targetUser.userId());
        CertificateEntity certificateEntity = certificateJpaRepository.getReferenceById(targetCertificate.certificateId());

            NormalPostEntity normalPostEntity = new NormalPostEntity(
                    postContent.title(),
                    postContent.content(),
                    userEntity,
                    certificateEntity,
                    postContent.postType()
            );

        NormalPostEntity savedPost = normalPostJpaRepository.save(normalPostEntity);

        List<PostImageEntity> postImageEntities = postContent.imageUrls().stream()
                .map(imageUrl -> PostImageEntity.of(savedPost, Image.from(imageUrl)))
                .toList();

        postImageJpaRepository.saveAll(postImageEntities);
        return TargetPost.from(savedPost.getId());
    }

    @Override
    public TargetPost saveTipPost(TargetUser targetUser,
                            TargetCertificate targetCertificate,
                            PostContent postContent,
                            Set<RecommendTag> recommendTags) {
        UserEntity userEntity = userJpaRepository.getReferenceById(targetUser.userId());
        CertificateEntity certificateEntity = certificateJpaRepository.getReferenceById(targetCertificate.certificateId());

        TipPostEntity tipPostEntity = new TipPostEntity(
                postContent.title(),
                postContent.content(),
                userEntity,
                certificateEntity,
                postContent.postType()
        );

        TipPostEntity savedPost = tipPostJpaRepository.save(tipPostEntity);

        List<PostImageEntity> postImageEntities = postContent.imageUrls().stream()
                .map(imageUrl -> PostImageEntity.of(savedPost, Image.from(imageUrl)))
                .toList();

        List<RecommendTagEntity> recommendTagEntities = recommendTags.stream()
                .map(recommendTag -> RecommendTagEntity.of(savedPost, recommendTag))
                .toList();


        postImageJpaRepository.saveAll(postImageEntities);
        recommendTagJpaRepository.saveAll(recommendTagEntities);
        return TargetPost.from(savedPost.getId());
    }
}
