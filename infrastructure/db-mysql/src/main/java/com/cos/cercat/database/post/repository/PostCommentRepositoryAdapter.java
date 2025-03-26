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
    public Long save(PostComment postComment) {
        return postCommentJpaRepository.save(PostCommentEntity.from(postComment)).getId();
    }

    @Override
    public Optional<PostComment> find(CommentId commentId) {
        return postCommentJpaRepository.findById(commentId.commentId()).map(PostCommentEntity::toDomain);
    }

    @Override
    public SliceResult<PostComment> findCommentsByUser(User user, Cursor cursor) {
        Slice<PostComment> postComments = postCommentJpaRepository.findByUserId(user.getId(), toPageRequest(cursor))
                .map(PostCommentEntity::toDomain);

        return SliceResult.of(postComments.getContent(), postComments.hasNext());
    }

    @Override
    public List<PostComment> findCommentsByPost(PostId postId) {
        return postCommentJpaRepository.findByPostId(postId.postId()).stream()
                .map(PostCommentEntity::toDomain)
                .toList();
    }

    @Override
    public void deleteComment(PostComment postComment) {
        PostCommentEntity commentEntity = postCommentJpaRepository.getReferenceById(postComment.getId());
        postCommentJpaRepository.delete(commentEntity);
    }

}
