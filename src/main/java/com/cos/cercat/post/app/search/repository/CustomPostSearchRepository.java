package com.cos.cercat.post.app.search.repository;

import com.cos.cercat.post.app.search.domain.PostDocument;
import com.cos.cercat.post.app.search.dto.SearchCond;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CustomPostSearchRepository {

    Slice<PostDocument> search(SearchCond cond, Long certificateId, Pageable pageable);

}
