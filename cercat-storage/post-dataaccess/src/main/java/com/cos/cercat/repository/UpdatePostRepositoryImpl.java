package com.cos.cercat.repository;

import com.cos.cercat.domain.PostCommentEntity;
import com.cos.cercat.domain.PostEntity;
import com.cos.cercat.domain.RecommendTagEntity;
import com.cos.cercat.domain.TipPostEntity;
import com.cos.cercat.post.Post;
import com.cos.cercat.post.PostComment;
import com.cos.cercat.post.TipPost;
import com.cos.cercat.post.UpdatePostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cos.cercat.PostMapper.toPostImageEntities;
import static com.cos.cercat.PostMapper.toRecommendTagEntities;

@Repository
@RequiredArgsConstructor
public class UpdatePostRepositoryImpl implements UpdatePostRepository {

    private final PostJpaRepository postJpaRepository;
    private final TipPostJpaRepository tipPostJpaRepository;
    private final PostCommentJpaRepository postCommentJpaRepository;
    private final PostImageJpaRepository postImageJpaRepository;
    private final RecommendTagJpaRepository recommendTagJpaRepository;


    @Override
    public void update(Post post) {
        PostEntity saved = postJpaRepository.save(PostEntity.from(post));
        postImageJpaRepository.saveAll(toPostImageEntities(post.getPostContent(), saved));
    }

    @Override
    public void updateComment(PostComment comment) {
        PostEntity postEntity = postJpaRepository.getReferenceById(comment.getPostId());
        postCommentJpaRepository.save(PostCommentEntity.of(comment, postEntity));
    }

    @Override
    public void updateTipPost(TipPost tipPost) {
        TipPostEntity saved = tipPostJpaRepository.save(TipPostEntity.from(tipPost));
        List<RecommendTagEntity> recommendTagEntities = toRecommendTagEntities(tipPost.getRecommendTags(), saved);
        recommendTagJpaRepository.saveAll(recommendTagEntities);
        postImageJpaRepository.saveAll(toPostImageEntities(tipPost.getPostContent(), saved));
    }

}
