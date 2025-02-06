package com.cos.cercat.es.postsearch.repository;


import com.cos.cercat.es.postsearch.entity.PostDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PostSearchElasticRepository extends ElasticsearchRepository<PostDocument, Long>, CustomPostSearchRepository{

}
