package com.cos.cercat.repository;

import com.cos.cercat.domain.PostDocument;
import com.cos.cercat.search.SearchCond;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface CustomPostSearchRepository {

    Slice<PostDocument> search(SearchCond cond, Long certificateId, Pageable pageable);

    List<String> getAutoCompletedKeywords(Long certificateId, String searchText);

    List<String> getRecentTop10Keywords(Long certificateId);

}
