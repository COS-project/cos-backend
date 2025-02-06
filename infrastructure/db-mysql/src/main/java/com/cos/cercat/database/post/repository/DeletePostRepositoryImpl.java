package com.cos.cercat.database.post.repository;

import com.cos.cercat.database.post.entity.PostEntity;
import com.cos.cercat.domain.post.DeletePostRepository;
import com.cos.cercat.domain.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DeletePostRepositoryImpl implements DeletePostRepository {

    private final RecommendTagJpaRepository recommendTagJpaRepository;
    private final PostImageJpaRepository postImageJpaRepository;
    private final PostJpaRepository postJpaRepository;

    @Override
    public void deleteRecommendTags(Post post) {
        recommendTagJpaRepository.deleteByPostId(post.getId());
    }

    @Override
    public void deleteImages(List<Long> removeImageIds) {
        postImageJpaRepository.deleteAllByImageId(removeImageIds);
    }

    @Override
    public void delete(Post post) {
        PostEntity postEntity = postJpaRepository.getReferenceById(post.getId());
        postJpaRepository.delete(postEntity);
    }

}
