package com.cos.cercat.post.app.search.repository;

import com.cos.cercat.post.app.search.domain.PostDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface PostSearchRepository extends ElasticsearchRepository<PostDocument, Long>, CustomPostSearchRepository{

}
