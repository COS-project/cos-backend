package com.cos.cercat.domain.post;

import java.util.List;

public interface DeletePostRepository {
    void deleteRecommendTags(Post post);

    void deleteImages(List<Long> removeImageIds);

    void delete(Post post);
}
