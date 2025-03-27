package com.cos.cercat.database.post.repository;

import com.cos.cercat.database.certificate.entity.QCertificateEntity;
import com.cos.cercat.database.common.entity.QImageEntity;
import com.cos.cercat.database.common.util.PagingUtil;
import com.cos.cercat.database.like.entity.QLikeCountEntity;
import com.cos.cercat.database.post.entity.PostEntity;
import com.cos.cercat.database.post.entity.PostImageEntity;
import com.cos.cercat.database.post.entity.QPostEntity;
import com.cos.cercat.database.post.entity.QPostImageEntity;
import com.cos.cercat.database.post.entity.QRecommendTagEntity;
import com.cos.cercat.database.post.entity.RecommendTagEntity;
import com.cos.cercat.database.user.entity.QUserEntity;
import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.common.Image;
import com.cos.cercat.domain.like.LikeTargetType;
import com.cos.cercat.domain.post.Post;
import com.cos.cercat.domain.post.PostType;
import com.cos.cercat.domain.searchlog.SearchCond;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final QPostEntity postEntity = QPostEntity.postEntity;
    private final QPostImageEntity postImageEntity = QPostImageEntity.postImageEntity;
    private final QImageEntity imageEntity = QImageEntity.imageEntity;
    private final QUserEntity userEntity = QUserEntity.userEntity;
    private final QCertificateEntity certificateEntity = QCertificateEntity.certificateEntity;
    private final QRecommendTagEntity recommendTagEntity = QRecommendTagEntity.recommendTagEntity;
    private final QLikeCountEntity likeCountEntity = QLikeCountEntity.likeCountEntity;

    @Override
    public Slice<Post> findPosts(SearchCond cond, Certificate certificate, Pageable pageable) {
        List<PostEntity> posts = findPostEntities(cond, certificate, pageable);
        boolean hasNext = applyPaging(posts, pageable);
        List<Long> postIds = extractPostIds(posts);
        Map<Long, List<Image>> postImageMap = findPostImages(postIds);
        Map<Long, Set<RecommendTagEntity>> postTagMap = findRecommendTags(postIds, cond.postType());
        List<Post> result = toDomain(posts, postImageMap, postTagMap);
        return new SliceImpl<>(result, pageable, hasNext);
    }

    private List<PostEntity> findPostEntities(SearchCond cond, Certificate certificate, Pageable pageable) {
        return queryFactory
                .selectFrom(postEntity)
                .leftJoin(postEntity.userEntity, userEntity).fetchJoin()
                .leftJoin(postEntity.certificateEntity, certificateEntity).fetchJoin()
                .leftJoin(likeCountEntity)
                .on(likeConditionForPost())
                .where(
                        eqCertificateId(certificate.id().value()),
                        eqPostType(cond.postType()),
                        fullTextSearch(cond.keyword())
                )
                .orderBy(postSort(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();
    }

    private boolean applyPaging(List<PostEntity> posts, Pageable pageable) {
        boolean hasNext = false;
        if (posts.size() > pageable.getPageSize()) {
            posts.remove(posts.size() - 1);
            hasNext = true;
        }
        return hasNext;
    }

    private List<Long> extractPostIds(List<PostEntity> posts) {
        return posts.stream()
                .map(PostEntity::getId)
                .collect(Collectors.toList());
    }

    private Map<Long, List<Image>> findPostImages(List<Long> postIds) {
        if (postIds.isEmpty()) {
            return Collections.emptyMap();
        }

        List<PostImageEntity> postImages = queryFactory
                .selectFrom(postImageEntity)
                .join(postImageEntity.image, imageEntity).fetchJoin()
                .where(postImageEntity.postImageId.postId.in(postIds))
                .fetch();

        return postImages.stream()
                .collect(Collectors.groupingBy(
                        pi -> pi.getPostImageId().getPostId(),
                        Collectors.mapping(PostImageEntity::toDomain, Collectors.toList())
                ));
    }

    private Map<Long, Set<RecommendTagEntity>> findRecommendTags(List<Long> postIds, PostType postType) {
        if (postIds.isEmpty() || postType != PostType.TIP) {
            return Collections.emptyMap();
        }

        Set<RecommendTagEntity> recommendTags = new HashSet<>(queryFactory
                .selectFrom(recommendTagEntity)
                .where(recommendTagEntity.postId.in(postIds))
                .fetch());

        return recommendTags.stream()
                .collect(Collectors.groupingBy(
                        RecommendTagEntity::getPostId,
                        Collectors.toSet()
                ));
    }

    private List<Post> toDomain(
            List<PostEntity> posts,
            Map<Long, List<Image>> postImageMap,
            Map<Long, Set<RecommendTagEntity>> postTagMap) {

        return posts.stream()
                .map(post -> {
                    List<Image> images = postImageMap.getOrDefault(post.getId(), Collections.emptyList());
                    if (post.getPostType() == PostType.TIP) {
                        Set<RecommendTagEntity> tags = postTagMap.getOrDefault(post.getId(), Collections.emptySet());
                        return post.toDomain(images, tags);
                    } else {
                        return post.toDomain(images);
                    }
                })
                .toList();
    }

    private BooleanExpression eqCertificateId(Long certificateId) {
        return certificateId != null ? postEntity.certificateEntity.id.eq(certificateId) : null;
    }

    private BooleanExpression eqPostType(PostType postType) {
        return postType != null ? postEntity.postType.eq(postType) : null;
    }

    private BooleanExpression fullTextSearch(String keyword) {
        if(keyword == null || keyword.isBlank()) {
            return null;
        }

        NumberTemplate<Double> matchScore = Expressions.numberTemplate(
                Double.class,
                "function('match', {0}, {1}, {2})",
                postEntity.title,
                postEntity.content,
                keyword
        );

        return matchScore.gt(0.0);
    }

    private BooleanExpression likeConditionForPost() {
        return postEntity.id.eq(likeCountEntity.id.targetId)
                .and(likeCountEntity.id.targetType.eq(LikeTargetType.POST));
    }

    private OrderSpecifier<?> postSort(Pageable pageable) {
        return PagingUtil.getOrderSpecifier(pageable,
                postEntity.createdAt,
                likeCountEntity.count);
    }
}
