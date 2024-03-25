package com.cos.cercat.search.repository;

import com.cos.cercat.search.domain.PostDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface PostSearchRepository extends ElasticsearchRepository<PostDocument, Long>, CustomPostSearchRepository{

}
