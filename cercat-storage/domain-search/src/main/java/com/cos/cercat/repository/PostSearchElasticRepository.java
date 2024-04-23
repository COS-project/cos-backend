package com.cos.cercat.repository;


import com.cos.cercat.domain.PostDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PostSearchElasticRepository extends ElasticsearchRepository<PostDocument, Long>, CustomPostSearchRepository{

}
