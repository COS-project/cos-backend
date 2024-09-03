package com.cos.cercat.infra.repository;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.post.*;

import com.cos.cercat.domain.user.User;
import com.cos.cercat.infra.entity.CertificateEntity;
import com.cos.cercat.infra.entity.CommentaryPostEntity;
import com.cos.cercat.infra.entity.NormalPostEntity;
import com.cos.cercat.infra.entity.PostCommentEntity;
import com.cos.cercat.infra.entity.PostEntity;
import com.cos.cercat.infra.entity.PostImageEntity;
import com.cos.cercat.infra.entity.QuestionEntity;
import com.cos.cercat.infra.entity.RecommendTagEntity;
import com.cos.cercat.infra.entity.TipPostEntity;
import com.cos.cercat.infra.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.cos.cercat.infra.PostMapper.toPostImageEntities;
import static com.cos.cercat.infra.PostMapper.toRecommendTagEntities;

@Repository
@RequiredArgsConstructor
@Transactional
public class CreatePostRepositoryImpl implements CreatePostRepository {

    private final CommentaryPostJpaRepository commentaryPostJpaRepository;
    private final NormalPostJpaRepository normalPostJpaRepository;
    private final TipPostJpaRepository tipPostJpaRepository;
    private final PostImageJpaRepository postImageJpaRepository;
    private final RecommendTagJpaRepository recommendTagJpaRepository;
    private final PostCommentJpaRepository postCommentJpaRepository;

    @Override
    public TargetPost saveCommentaryPost(User user,
                                         Certificate certificate,
                                         PostContent postContent,
                                         Question question) {
        CommentaryPostEntity commentaryPostEntity = new CommentaryPostEntity(
                postContent.getTitle(),
                postContent.getContent(),
                UserEntity.from(user),
                CertificateEntity.from(certificate),
                PostType.COMMENTARY,
                QuestionEntity.from(question)
        );

        CommentaryPostEntity savedPost = commentaryPostJpaRepository.save(commentaryPostEntity);
        List<PostImageEntity> postImageEntities = toPostImageEntities(postContent, savedPost);
        postImageJpaRepository.saveAll(postImageEntities);
        return TargetPost.from(savedPost.getId());
    }

    @Override
    public TargetPost saveNormalPost(User user, Certificate certificate, PostContent postContent) {

        NormalPostEntity normalPostEntity = new NormalPostEntity(
                postContent.getTitle(),
                postContent.getContent(),
                UserEntity.from(user),
                CertificateEntity.from(certificate),
                PostType.NORMAL
        );
        NormalPostEntity savedPost = normalPostJpaRepository.save(normalPostEntity);
        List<PostImageEntity> postImageEntities = toPostImageEntities(postContent, savedPost);
        postImageJpaRepository.saveAll(postImageEntities);
        return TargetPost.from(savedPost.getId());
    }

    @Override
    public TargetPost saveTipPost(User user,
                                  Certificate certificate,
                                  PostContent postContent,
                                  Set<RecommendTag> recommendTags) {
        TipPostEntity tipPostEntity = new TipPostEntity(
                postContent.getTitle(),
                postContent.getContent(),
                UserEntity.from(user),
                CertificateEntity.from(certificate),
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
    public void saveComment(User user,
                            Post post,
                            CommentContent content) {
        PostCommentEntity newPostCommentEntity = PostCommentEntity.of(
                UserEntity.from(user),
                PostEntity.from(post),
                content.parentId(),
                content.content()
        );

        postCommentJpaRepository.save(newPostCommentEntity);
    }
}
