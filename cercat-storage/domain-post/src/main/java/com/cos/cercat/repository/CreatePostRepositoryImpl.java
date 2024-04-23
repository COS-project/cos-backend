package com.cos.cercat.repository;

import com.cos.cercat.domain.*;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.post.*;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.cos.cercat.PostMapper.toPostImageEntities;
import static com.cos.cercat.PostMapper.toRecommendTagEntities;

@Repository
@RequiredArgsConstructor
public class CreatePostRepositoryImpl implements CreatePostRepository {

    private final CertificateJpaRepository certificateJpaRepository;
    private final CommentaryPostJpaRepository commentaryPostJpaRepository;
    private final NormalPostJpaRepository normalPostJpaRepository;
    private final TipPostJpaRepository tipPostJpaRepository;
    private final PostImageJpaRepository postImageJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final QuestionJpaRepository questionJpaRepository;
    private final RecommendTagJpaRepository recommendTagJpaRepository;
    private final PostCommentJpaRepository postCommentJpaRepository;

    @Override
    public TargetPost saveCommentaryPost(TargetUser targetUser,
                                         TargetCertificate targetCertificate,
                                         PostContent postContent,
                                         Question question) {

        UserEntity userEntity = userJpaRepository.getReferenceById(targetUser.userId());
        QuestionEntity questionEntity = questionJpaRepository.getReferenceById(question.id());
        CertificateEntity certificateEntity = certificateJpaRepository.getReferenceById(targetCertificate.certificateId());


        CommentaryPostEntity commentaryPostEntity = new CommentaryPostEntity(
                postContent.getTitle(),
                postContent.getContent(),
                userEntity,
                certificateEntity,
                PostType.COMMENTARY,
                questionEntity
        );

        CommentaryPostEntity savedPost = commentaryPostJpaRepository.save(commentaryPostEntity);
        List<PostImageEntity> postImageEntities = toPostImageEntities(postContent, savedPost);
        postImageJpaRepository.saveAll(postImageEntities);
        return TargetPost.from(savedPost.getId());
    }

    @Override
    public TargetPost saveNormalPost(TargetUser targetUser, TargetCertificate targetCertificate, PostContent postContent) {
        UserEntity userEntity = userJpaRepository.getReferenceById(targetUser.userId());
        CertificateEntity certificateEntity = certificateJpaRepository.getReferenceById(targetCertificate.certificateId());

        NormalPostEntity normalPostEntity = new NormalPostEntity(
                postContent.getTitle(),
                postContent.getContent(),
                userEntity,
                certificateEntity,
                PostType.NORMAL
        );

        NormalPostEntity savedPost = normalPostJpaRepository.save(normalPostEntity);
        List<PostImageEntity> postImageEntities = toPostImageEntities(postContent, savedPost);
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
                postContent.getTitle(),
                postContent.getContent(),
                userEntity,
                certificateEntity,
                PostType.TIP
        );

        TipPostEntity savedPost = tipPostJpaRepository.save(tipPostEntity);
        List<PostImageEntity> postImageEntities = toPostImageEntities(postContent, savedPost);
        List<RecommendTagEntity> recommendTagEntities = toRecommendTagEntities(recommendTags, savedPost);

        postImageJpaRepository.saveAll(postImageEntities);
        recommendTagJpaRepository.saveAll(recommendTagEntities);
        return TargetPost.from(savedPost.getId());
    }

    @Override
    public void saveComment(TargetUser targetUser,
                            TargetPost targetPost,
                            CommentContent content) {
        UserEntity userEntity = userJpaRepository.getReferenceById(targetUser.userId());
        PostEntity postEntity = commentaryPostJpaRepository.getReferenceById(targetPost.postId());

        PostCommentEntity newPostCommentEntity = PostCommentEntity.of(
                userEntity,
                postEntity,
                content.parentId(),
                content.content()
        );

        postCommentJpaRepository.save(newPostCommentEntity);
    }
}
