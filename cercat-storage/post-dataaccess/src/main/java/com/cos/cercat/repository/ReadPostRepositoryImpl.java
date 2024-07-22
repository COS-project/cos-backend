package com.cos.cercat.repository;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.Image;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.domain.*;
import com.cos.cercat.exception.CommentNotFoundException;
import com.cos.cercat.exception.PostNotFoundException;
import com.cos.cercat.post.*;
import com.cos.cercat.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Transactional
public class ReadPostRepositoryImpl implements ReadPostRepository {

    private final CommentaryPostJpaRepository commentaryPostJpaRepository;
    private final NormalPostJpaRepository normalPostJpaRepository;
    private final TipPostJpaRepository tipPostJpaRepository;
    private final PostJpaRepository postJpaRepository;
    private final PostCommentJpaRepository postCommentJpaRepository;
    private final PostImageJpaRepository postImageJpaRepository;
    private final RecommendTagJpaRepository recommendTagJpaRepository;

    @Override
    @Transactional
    public SliceResult<Post> search(Certificate certificate,
                                    CommentaryPostSearchCond commentaryPostSearchCond,
                                    Cursor cursor) {

        Slice<CommentaryPostEntity> commentaryPostEntities = commentaryPostJpaRepository.searchPosts(
                toPageRequest(cursor),
                CertificateEntity.from(certificate),
                commentaryPostSearchCond
        );

        List<Post> commentaryPosts = commentaryPostEntities.stream()
                .map(this::toPost)
                .toList();

        return SliceResult.of(commentaryPosts, commentaryPostEntities.hasNext());
    }

    @Override
    public PostWithComments findDetail(TargetPost targetPost) {
        PostEntity postEntity = postJpaRepository.findById(targetPost.postId()).orElseThrow(
                () -> PostNotFoundException.EXCEPTION);

        Post post = toPost(postEntity);

        List<PostComment> postComments = postCommentJpaRepository.findByPostId(targetPost.postId()).stream()
                .map(PostCommentEntity::toDomain)
                .toList();

        return PostWithComments.of(post, postComments);
    }

    @Override
    public List<Post> findTop3TipPosts(Certificate certificate) {

        return tipPostJpaRepository.findTop3(certificate.id()).stream()
                .map(this::toPost)
                .toList();
    }

    @Override
    public SliceResult<Post> findMyCommentaryPosts(User user, Cursor cursor) {
        Slice<CommentaryPostEntity> commentaryPostEntities =
                commentaryPostJpaRepository.findByUserId(user.getId(), toPageRequest(cursor));

        List<Post> commentaryPosts = commentaryPostEntities.stream()
                .map(this::toPost)
                .toList();
        return SliceResult.of(commentaryPosts, commentaryPostEntities.hasNext());
    }

    @Override
    public SliceResult<Post> findMyNormalPosts(User user, Cursor cursor) {

        Slice<Post> posts = normalPostJpaRepository.findNormalPostsByUserId(user.getId(), toPageRequest(cursor))
                .map(this::toPost);

        return SliceResult.of(posts.getContent(), posts.hasNext());
    }

    @Override
    public SliceResult<Post> findMyTipPosts(User user, Cursor cursor) {
        Slice<Post> posts = tipPostJpaRepository.findTipPostsByUserId(user.getId(), toPageRequest(cursor))
                .map(this::toPost);

        return SliceResult.of(posts.getContent(), posts.hasNext());
    }

    @Override
    public SliceResult<PostComment> findComment(User user, Cursor cursor) {
        Slice<PostComment> postComments = postCommentJpaRepository.findByUserId(user.getId(), toPageRequest(cursor))
                .map(PostCommentEntity::toDomain);

        return SliceResult.of(postComments.getContent(), postComments.hasNext());
    }

    @Override
    @Transactional
    public Post findWithLock(TargetPost targetPost) {
        PostEntity postEntity = postJpaRepository.findByIdWithPessimisticLock(targetPost.postId()).orElseThrow(
                () -> PostNotFoundException.EXCEPTION);
        return toPost(postEntity);
    }

    @Override
    public Post find(TargetPost targetPost) {
        PostEntity postEntity = postJpaRepository.findById(targetPost.postId()).orElseThrow(
                () -> PostNotFoundException.EXCEPTION);
        return toPost(postEntity);
    }

    @Override
    public PostComment find(TargetComment targetComment) {
        return postCommentJpaRepository.findById(targetComment.commentId()).orElseThrow(
                () -> CommentNotFoundException.EXCEPTION)
                .toDomain();
    }

    @Override
    public PostComment findCommentWithLock(TargetComment targetComment) {
        return postCommentJpaRepository.findByIdWithPessimisticLock(targetComment.commentId()).orElseThrow(
                        () -> CommentNotFoundException.EXCEPTION)
                .toDomain();
    }

    private Set<RecommendTag> getRecommendTags(TargetPost targetPost) {
        return recommendTagJpaRepository.findByPostId(targetPost.postId()).stream()
                .map(RecommendTagEntity::toDomain)
                .collect(Collectors.toSet());
    }

    private int getCommentCount(TargetPost targetPost) {
        return postCommentJpaRepository.countByPostId(targetPost.postId());
    }


    private List<Image> getPostImages(TargetPost targetPost) {
        return postImageJpaRepository.findByPostId(targetPost.postId()).stream()
                .map(postImageEntity -> postImageEntity.getImageEntity().toImage())
                .toList();
    }

    private Post toPost(PostEntity entity) {
        int commentCount = getCommentCount(TargetPost.from(entity.getId()));
        Set<RecommendTag> recommendTags = getRecommendTags(TargetPost.from(entity.getId()));
        return entity.toDomain(getPostImages(TargetPost.from(entity.getId())), commentCount, recommendTags);
    }

    private PageRequest toPageRequest(Cursor cursor) {
        // 여러 정렬 기준을 처리할 Sort 객체 생성
        Sort sort = Sort.by(cursor.sortOrders().stream()
                .map(order -> new Sort.Order(
                        Sort.Direction.fromOptionalString(order.direction().name()).orElse(Sort.Direction.ASC),
                        order.key()
                ))
                .toList());

        return PageRequest.of(cursor.page(), cursor.size(), sort);
    }
}
