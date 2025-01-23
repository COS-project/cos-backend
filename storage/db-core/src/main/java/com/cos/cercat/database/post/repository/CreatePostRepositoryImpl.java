package com.cos.cercat.database.post.repository;

import static com.cos.cercat.database.post.PostMapper.toPostImageEntities;
import static com.cos.cercat.database.post.PostMapper.toRecommendTagEntities;

import com.cos.cercat.database.certificate.entity.CertificateEntity;
import com.cos.cercat.database.mockexam.entity.QuestionEntity;
import com.cos.cercat.database.user.entity.UserEntity;
import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.post.*;

import com.cos.cercat.domain.user.User;
import com.cos.cercat.database.post.entity.CommentaryPostEntity;
import com.cos.cercat.database.post.entity.NormalPostEntity;
import com.cos.cercat.database.post.entity.PostCommentEntity;
import com.cos.cercat.database.post.entity.PostEntity;
import com.cos.cercat.database.post.entity.PostImageEntity;
import com.cos.cercat.database.post.entity.RecommendTagEntity;
import com.cos.cercat.database.post.entity.TipPostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


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
                postContent.title(),
                postContent.content(),
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
                postContent.title(),
                postContent.content(),
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
                postContent.title(),
                postContent.content(),
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
