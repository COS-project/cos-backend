package com.cos.cercat.infra.repository;


import com.cos.cercat.infra.entity.PostDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PostSearchElasticRepository extends ElasticsearchRepository<PostDocument, Long>, CustomPostSearchRepository{

}
