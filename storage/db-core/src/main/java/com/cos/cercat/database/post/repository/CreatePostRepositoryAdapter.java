package com.cos.cercat.database.post.repository;


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
    public TargetPost save(Post post) {
        PostEntity postEntity = postJpaRepository.save(PostEntity.from(post));
        savePostImages(post);

        if (post.getType().equals(PostType.TIP)) {
            saveRecommendTags((TipPost) post);
        }

        return TargetPost.from(postEntity.getId());
    }

    private void savePostImages(Post post) {
        List<PostImageEntity> imageEntities = post.getPostImages().stream()
                .map(image -> PostImageEntity.of(post.getId(), image))
                .toList();
        postImageJpaRepository.saveAll(imageEntities);
    }

    private void saveRecommendTags(TipPost tipPost) {
        Set<RecommendTag> recommendTags = tipPost.getRecommendTags();
        recommendTagJpaRepository.saveAll(recommendTags.stream()
                .map(recommendTag -> RecommendTagEntity.of(tipPost.getId(), recommendTag))
                .toList());
    }
}
