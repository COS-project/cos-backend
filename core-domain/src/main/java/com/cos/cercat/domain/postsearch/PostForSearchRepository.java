package com.cos.cercat.domain.postsearch;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.post.TargetPost;

import java.util.List;

public interface PostForSearchRepository {

    void save(PostForSearch post);

    PostForSearch find(TargetPost targetPost);

    void delete(TargetPost targetPost);

    SliceResult<PostForSearch> search(SearchCond cond, Certificate certificate, Cursor cursor);

    List<String> findAutoCompletedKeywords(Certificate certificate, String searchText);

    List<String> findRecentTrendingKeywords(Certificate certificate);
}
