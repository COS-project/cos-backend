package com.cos.cercat.domain.search.repository;

import com.cos.cercat.domain.search.domain.PostDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface PostSearchRepository extends ElasticsearchRepository<PostDocument, Long>, CustomPostSearchRepository{

}
