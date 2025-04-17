package com.cos.cercat.database.post.repository;

import static com.cos.cercat.database.common.util.PagingUtil.toPageRequest;

import com.cos.cercat.database.certificate.entity.CertificateEntity;
import com.cos.cercat.database.post.entity.PostImageEntity;
import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.Image;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.post.*;
import com.cos.cercat.database.post.entity.CommentaryPostEntity;
import com.cos.cercat.database.post.entity.PostEntity;

import com.cos.cercat.domain.searchlog.SearchCond;
import com.cos.cercat.domain.user.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class ReadPostRepositoryImpl implements ReadPostRepository {

    private final CommentaryPostJpaRepository commentaryPostJpaRepository;
    private final PostJpaRepository postJpaRepository;
    private final PostImageJpaRepository postImageJpaRepository;
    private final RecommendTagJpaRepository recommendTagJpaRepository;

    @Override
    public Optional<Post> find(PostId postId) {
        return postJpaRepository.findById(postId.value()).map(this::toDomain);
    }

    @Override
    public Optional<Post> findForUpdate(PostId postId) {
        return postJpaRepository.findForUpdate(postId.value()).map(this::toDomain);
    }

    @Override
    public SliceResult<Post> findPosts(
            Certificate certificate,
            SearchCond cond,
            Cursor cursor
    ) {
        Slice<Post> posts = postJpaRepository.findPosts(
                cond,
                certificate,
                toPageRequest(cursor));

        return SliceResult.of(posts.getContent(), posts.hasNext());
    }

    @Override
    public SliceResult<Post> findCommentaries(
            Certificate certificate,
            CommentarySearchCond commentarySearchCond,
            Cursor cursor
    ) {

        Slice<CommentaryPostEntity> commentaryPostEntities = commentaryPostJpaRepository.searchPosts(
                toPageRequest(cursor),
                CertificateEntity.from(certificate),
                commentarySearchCond
        );

        List<Post> commentaryPosts = commentaryPostEntities.stream()
                .map(this::toDomain)
                .toList();

        return SliceResult.of(commentaryPosts, commentaryPostEntities.hasNext());
    }

    @Override
    public List<Post> findTop3TipPosts(Certificate certificate) {
        return postJpaRepository.findTop3Tips(certificate.id().value()).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public SliceResult<Post> findMyPosts(User user, Cursor cursor, PostType postType) {
        Slice<Post> postSlice = postJpaRepository.findByUserId(user.getId(), toPageRequest(cursor))
                .map(this::toDomain);
        return SliceResult.of(postSlice.getContent(), postSlice.hasNext());
    }

    private Post toDomain(PostEntity entity) {
        // 팁 게시물 일 경우에만 추천 태그를 가져온다.
        if (entity.getPostType().equals(PostType.TIP)) {
            return entity.toDomain(
                    getPostImages(entity.getId()),
                    recommendTagJpaRepository.findByPostId(entity.getId()));
        }

        return entity.toDomain(getPostImages(entity.getId()));
    }

    private List<Image> getPostImages(Long postId) {
        return postImageJpaRepository.findByPostId(postId).stream()
                .map(PostImageEntity::toDomain)
                .toList();
    }
}
