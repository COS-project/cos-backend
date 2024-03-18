package com.cos.cercat.post.app.search.repository;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import com.cos.cercat.post.app.search.domain.PostDocument;
import com.cos.cercat.post.app.search.dto.SearchCond;
import com.google.api.client.util.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CustomPostSearchRepositoryImpl implements CustomPostSearchRepository {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public Slice<PostDocument> search(SearchCond searchCond, Long certificateId, Pageable pageable) {

        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();
        List<Query> queries = Lists.newArrayList();

        if (certificateId != null) {
            Query query = QueryBuilders
                    .matchPhrase()
                    .field("certificateId")
                    .query(certificateId.toString())
                    .build()
                    ._toQuery();
            queries.add(query);
        }

        if (searchCond.postType() != null) {
            Query query = QueryBuilders.matchPhrase()
                    .field("postType")
                    .query(searchCond.postType().name())
                    .build()
                    ._toQuery();
            queries.add(query);
        }

        if (searchCond.keyword() != null && !searchCond.keyword().isEmpty()) {
            MultiMatchQuery.Builder multiMatchBuilder = QueryBuilders.multiMatch();
            Query query = multiMatchBuilder
                    .query(searchCond.keyword())
                    .fields(List.of("title^2",
                            "title.nori^2",
                            "title.ngram^2",
                            "content^2",
                            "content.nori^2",
                            "content.ngram^2",
                            "postComments.content",
                            "postComments.content.nori",
                            "postComments.content.ngram"))
                    .build()
                    ._toQuery();
            boolQueryBuilder.should(query);
        }


        Query query = boolQueryBuilder.must(queries).build()._toQuery();

        Pageable sizeUpPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize() + 1);

        NativeQuery nativeQuery = new NativeQueryBuilder()
                .withQuery(query)
                .withPageable(sizeUpPageable)
                .build();

        List<PostDocument> contents = elasticsearchOperations
                .search(nativeQuery, PostDocument.class)
                .map(SearchHit::getContent)
                .stream().collect(Collectors.toList());

        boolean hasNext = false;
        if (contents.size() > pageable.getPageSize()) {
            contents.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(contents, pageable, hasNext);
    }
}
