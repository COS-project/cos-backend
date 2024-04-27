package com.cos.cercat.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import co.elastic.clients.util.NamedValue;
import co.elastic.clients.util.ObjectBuilder;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.PostDocument;
import com.cos.cercat.search.SearchCond;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CustomPostSearchRepositoryImpl implements CustomPostSearchRepository {

    private final ElasticsearchOperations elasticsearchOperations;
    private final String KEYWORD_KEYWORD = "keyword.keyword";
    private final String KEYWORD_EDGE_NGRAM = "keyword.edge_ngram";
    private final String KEYWORD_NORI = "keyword.nori";
    private final String URI_FIELD = "uri";
    private final String URI_PREFIX = "/api/v1/certificates/";
    private final String URI_SEARCH = "/search";
    private final String MINIMUM_SHOULD_MATCH = "1";
    private final String LOG_INDEX_PREFIX = "logstash-";
    private final String KEYWORD_AGGREGATION = "keywords";
    private final String POST_TYPE_FIELD = "postType";
    private final String CERTIFICATE_ID_FIELD = "certificateId";
//    private final String TIME_FIELD = "time";

    @Value("${elasticsearch.client.host}")
    private String ES_HOST;
    @Value("${elasticsearch.port}")
    private int ES_PORT;

    @Override
    public Slice<PostDocument> search(SearchCond searchCond, Long certificateId, Pageable pageable) {

        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();
        List<Query> queries = new ArrayList<>();

        if (certificateId != null) {
            Query query = QueryBuilders
                    .matchPhrase()
                    .field(CERTIFICATE_ID_FIELD)
                    .query(certificateId.toString())
                    .build()
                    ._toQuery();
            queries.add(query);
        }

        if (searchCond.postType() != null) {
            Query query = QueryBuilders.matchPhrase()
                    .field(POST_TYPE_FIELD)
                    .query(searchCond.postType().name())
                    .build()
                    ._toQuery();
            queries.add(query);
        }

        if (searchCond.keyword() != null && !searchCond.keyword().isEmpty()) {
            MultiMatchQuery.Builder multiMatchBuilder = QueryBuilders.multiMatch();
            Query query = multiMatchBuilder
                    .query(searchCond.keyword())
                    .fields(List.of(
                            "title^1.5",
                            "title.nori^1.5",
                            "title.ngram",
                            "content^1.5",
                            "content.nori^1.5",
                            "content.ngram",
                            "postComments.content",
                            "postComments.content.nori",
                            "postComments.content.ngram"))
                    .build()
                    ._toQuery();
            queries.add(query);
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


    @Override
    public List<String> getAutoCompletedKeywords(Long certificateId, String searchText) {
        ElasticsearchClient client = createElasticsearchClient();

        Query query = createAutoCompletedQuery(certificateId, searchText);

        try {
            SearchResponse<Void> response = client.search(s -> s
                            .index(LOG_INDEX_PREFIX + "*")
                            .size(0)
                            .query(query)
                            .aggregations(KEYWORD_AGGREGATION, keywordAggregation()
                            ),
                    Void.class
            );

            return response.aggregations().get(KEYWORD_AGGREGATION).sterms().buckets().array().stream()
                    .map(StringTermsBucket::key)
                    .map(FieldValue::stringValue)
                    .toList();

        } catch (IOException e) {
            throw new CustomException(ErrorCode.ES_SEARCH_ERROR);
        }
    }

    @Override
    public List<String> getRecentTop5Keywords(Long certificateId) {
        ElasticsearchClient client = createElasticsearchClient();

        Query query = createRecentTop5Query(certificateId);

        try {
            SearchResponse<Void> response = client.search(s -> s
                            .index(LOG_INDEX_PREFIX + extractDate())
                            .size(0)
                            .query(query)
                            .aggregations(KEYWORD_AGGREGATION, keywordAggregation()), Void.class);

            return response.aggregations().get(KEYWORD_AGGREGATION).sterms().buckets().array().stream()
                    .map(StringTermsBucket::key)
                    .map(FieldValue::stringValue)
                    .toList();

        } catch (IOException e) {
            throw new CustomException(ErrorCode.ES_SEARCH_ERROR);
        }
    }

    private Query createAutoCompletedQuery(Long certificateId, String searchText) {
        return BoolQuery.of(b -> b
                .filter(f -> f
                        .match(m -> m
                                .field(URI_FIELD)
                                .query(URI_PREFIX + certificateId + URI_SEARCH)
                        )
                ).must(m -> m.
                        bool(b1 -> b1
                                .should(s -> s
                                        .term(t -> t
                                                .field(KEYWORD_KEYWORD)
                                                .value(searchText)
                                        )
                                ).should(s -> s
                                        .term(t -> t
                                                .field(KEYWORD_EDGE_NGRAM)
                                                .value(searchText)
                                        )
                                ).should(s -> s
                                        .term(t -> t
                                                .field(KEYWORD_NORI)
                                                .value(searchText)
                                        )
                                ).minimumShouldMatch(MINIMUM_SHOULD_MATCH)
                        )
                )
        )._toQuery();
    }

    private Query createRecentTop5Query(Long certificateId) {
        return BoolQuery.of(b -> b
//                .filter(f -> f
//                        .range(r -> r
//                                .field(TIME_FIELD)
//                                .gte(JsonData.of("now-2h/h"))
//                                .lte(JsonData.of("now/h"))
//                        )
//                ) // 유저수가 적어서 최근 2시간 검색어가 없을 수 있음 -> 향후 변경
                .filter(f -> f
                        .match(m -> m
                                .field(URI_FIELD)
                                .query(URI_PREFIX + certificateId + URI_SEARCH)
                        )
                ))._toQuery();
    }

    private String extractDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return today.format(formatter);
    }

    private Function<Aggregation.Builder, ObjectBuilder<Aggregation>> keywordAggregation() {
        return a -> a
                .terms(t -> t
                        .field(KEYWORD_KEYWORD)
                        .size(5)
                        .order(List.of(NamedValue.of("_count", SortOrder.Desc)))
                );
    }

    private ElasticsearchClient createElasticsearchClient() {
        RestClient restClient = RestClient.builder(new HttpHost(ES_HOST, ES_PORT)).build();
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }
}
