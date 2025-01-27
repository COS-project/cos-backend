package com.cos.cercat.database.post.repository;

import static com.cos.cercat.database.common.util.PagingUtil.toPageRequest;

import com.cos.cercat.database.post.entity.PostCommentEntity;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.post.PostComment;
import com.cos.cercat.domain.post.PostCommentRepository;
import com.cos.cercat.domain.post.TargetComment;
import com.cos.cercat.domain.post.TargetPost;
import com.cos.cercat.domain.user.User;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostCommentRepositoryAdapter implements PostCommentRepository {

    private final PostCommentJpaRepository postCommentJpaRepository;

    @Override
    public Long save(PostComment postComment) {
        return postCommentJpaRepository.save(PostCommentEntity.from(postComment)).getId();
    }

    @Override
    public Optional<PostComment> find(TargetComment targetComment) {
        return postCommentJpaRepository.findById(targetComment.commentId()).map(PostCommentEntity::toDomain);
    }

    @Override
    public SliceResult<PostComment> findCommentsByUser(User user, Cursor cursor) {
        Slice<PostComment> postComments = postCommentJpaRepository.findByUserId(user.getId(), toPageRequest(cursor))
                .map(PostCommentEntity::toDomain);

        return SliceResult.of(postComments.getContent(), postComments.hasNext());
    }

    @Override
    public List<PostComment> findCommentsByPost(TargetPost targetPost) {
        return postCommentJpaRepository.findByPostId(targetPost.postId()).stream()
                .map(PostCommentEntity::toDomain)
                .toList();
    }

    @Override
    public void deleteComment(PostComment postComment) {
        PostCommentEntity commentEntity = postCommentJpaRepository.getReferenceById(postComment.getId());
        postCommentJpaRepository.delete(commentEntity);
    }

}
