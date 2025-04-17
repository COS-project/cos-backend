package com.cos.cercat.database.post.repository;

import static com.cos.cercat.database.common.util.PagingUtil.toPageRequest;

import com.cos.cercat.database.post.entity.PostCommentEntity;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.post.PostComment;
import com.cos.cercat.domain.post.PostCommentRepository;
import com.cos.cercat.domain.post.CommentId;
import com.cos.cercat.domain.post.PostId;
import com.cos.cercat.domain.user.User;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class PostCommentRepositoryAdapter implements PostCommentRepository {

    private final PostCommentJpaRepository postCommentJpaRepository;

    @Override
    public PostComment save(PostComment postComment) {
        return postCommentJpaRepository.save(PostCommentEntity.from(postComment)).toDomain();
    }

    @Override
    public Optional<PostComment> find(CommentId commentId) {
        return postCommentJpaRepository.findById(commentId.commentId()).map(PostCommentEntity::toDomain);
    }

    @Override
    public SliceResult<Long> findpostIdsByUser(User user, Cursor cursor) {
        return postCommentJpaRepository.findDistinctPostIdsByUser(
                user, toPageRequest(cursor));
    }

    @Override
    public List<PostComment> findCommentsByPost(PostId postId) {
        return postCommentJpaRepository.findByPostId(postId.value()).stream()
                .map(PostCommentEntity::toDomain)
                .toList();
    }

    @Override
    public void deleteComment(PostComment postComment) {
        PostCommentEntity commentEntity = postCommentJpaRepository.getReferenceById(postComment.getId());
        postCommentJpaRepository.delete(commentEntity);
    }

}
