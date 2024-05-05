package com.cos.cercat.search;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.post.TargetPost;

import java.util.List;

public interface PostForSearchRepository {

    void save(PostForSearch post);

    PostForSearch find(TargetPost targetPost);

    void delete(TargetPost targetPost);

    SliceResult<PostForSearch> search(SearchCond cond, Certificate certificate, Cursor cursor);

    List<String> findAutoCompletedKeywords(Certificate certificate, String searchText);

    List<String> findRecentTop10Keywords(Certificate certificate);
}
