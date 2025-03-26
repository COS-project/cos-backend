package com.cos.cercat.database.post.repository;


import com.cos.cercat.domain.common.Image;
import com.cos.cercat.domain.post.*;

import com.cos.cercat.database.post.entity.PostEntity;
import com.cos.cercat.database.post.entity.PostImageEntity;
import com.cos.cercat.database.post.entity.RecommendTagEntity;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@RequiredArgsConstructor
@Transactional
public class CreatePostRepositoryAdapter implements CreatePostRepository {

    private final PostJpaRepository postJpaRepository;
    private final PostImageJpaRepository postImageJpaRepository;
    private final RecommendTagJpaRepository recommendTagJpaRepository;


    @Override
    public PostId save(Post post) {
        PostEntity postEntity = postJpaRepository.save(PostEntity.from(post));
        savePostImages(postEntity.getId(), post.getPostImages());

        if (post.getType().equals(PostType.TIP)) {
            saveRecommendTags(postEntity.getId(), (TipPost) post);
        }

        return PostId.from(postEntity.getId());
    }

    private void savePostImages(Long postId, List<Image> postImages) {
        List<PostImageEntity> imageEntities = postImages.stream()
                .map(image -> PostImageEntity.of(postId, image))
                .toList();
        postImageJpaRepository.saveAll(imageEntities);
    }

    private void saveRecommendTags(Long postId, TipPost tipPost) {
        Set<RecommendTag> recommendTags = tipPost.getRecommendTags();
        recommendTagJpaRepository.saveAll(recommendTags.stream()
                .map(recommendTag -> RecommendTagEntity.of(postId, recommendTag))
                .toList());
    }
}
