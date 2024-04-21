package com.cos.cercat.repository.post;

import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.QuestionEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.comment.PostCommentEntity;
import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.post.*;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.entity.Image;
import com.cos.cercat.repository.CertificateJpaRepository;
import com.cos.cercat.repository.QuestionJpaRepository;
import com.cos.cercat.repository.UserJpaRepository;
import com.cos.cercat.repository.comment.PostCommentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public SliceResult<Post> find(TargetCertificate targetCertificate,
                                  CommentaryPostSearchCond commentaryPostSearchCond,
                                  Cursor cursor) {
        CertificateEntity certificateEntity = certificateJpaRepository.getReferenceById(targetCertificate.certificateId());
        Slice<CommentaryPostEntity> commentaryPostEntities = commentaryPostJpaRepository.searchPosts(toPageRequest(cursor), certificateEntity, commentaryPostSearchCond);

        List<Post> commentaryPosts = commentaryPostEntities.stream()
                .map(this::toPost)
                .toList();

        return SliceResult.of(commentaryPosts, commentaryPostEntities.hasNext());
    }

    @Override
    public PostWithComments findDetail(TargetPost targetPost) {
        Post post = postJpaRepository.findById(targetPost.postId()).orElseThrow(
                        () -> new CustomException(ErrorCode.POST_NOT_FOUND))
                .toDomain(
                        getPostImages(targetPost),
                        getCommentCount(targetPost),
                        getRecommendTags(targetPost)
                );

        List<PostComment> postComments = postCommentJpaRepository.findByPostId(targetPost.postId()).stream()
                .map(PostCommentEntity::toDomain)
                .toList();

        return PostWithComments.of(post, postComments);
    }

    private Post toPost(CommentaryPostEntity entity) {
        int commentCount = getCommentCount(TargetPost.from(entity.getId()));
        return entity.toDomain(getPostImages(TargetPost.from(entity.getId())), commentCount);
    }

    private Set<RecommendTag> getRecommendTags(TargetPost targetPost) {
        return recommendTagJpaRepository.findByPostId(targetPost.postId()).stream()
                .map(RecommendTagEntity::toDomain)
                .collect(Collectors.toSet());
    }

    private int getCommentCount(TargetPost targetPost) {
        return postCommentJpaRepository.countByPostId(targetPost.postId());
    }


    private List<String> getPostImages(TargetPost targetPost) {
        return postImageJpaRepository.findByPostId(targetPost.postId()).stream()
                .map(postImageEntity -> postImageEntity.getImage().getImageUrl())
                .toList();
    }

    private PageRequest toPageRequest(Cursor cursor) {
        Sort.Direction direction = Sort.Direction.fromOptionalString(cursor.sortDirection().name()).orElseThrow();
        return PageRequest.of(cursor.page(), cursor.limit(), Sort.by(direction, cursor.sortKey().key()));
    }
}
