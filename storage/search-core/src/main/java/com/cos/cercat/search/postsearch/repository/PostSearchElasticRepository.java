package com.cos.cercat.search.postsearch.repository;


import com.cos.cercat.search.postsearch.entity.PostDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PostSearchElasticRepository extends ElasticsearchRepository<PostDocument, Long>, CustomPostSearchRepository{

}
