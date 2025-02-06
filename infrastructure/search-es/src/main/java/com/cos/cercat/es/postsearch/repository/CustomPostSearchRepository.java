package com.cos.cercat.es.postsearch.repository;

import com.cos.cercat.domain.postsearch.SearchCond;
import com.cos.cercat.es.postsearch.entity.PostDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface CustomPostSearchRepository {

    Slice<PostDocument> search(SearchCond cond, Long certificateId, Pageable pageable);

    List<String> getAutoCompletedKeywords(Long certificateId, String searchText);

    List<String> getRecentTop10Keywords(Long certificateId);

}
