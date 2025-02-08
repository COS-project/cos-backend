package com.cos.cercat.es.postsearch.repository;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.postsearch.PostForSearch;
import com.cos.cercat.domain.postsearch.PostForSearchRepository;
import com.cos.cercat.domain.postsearch.SearchCond;
import com.cos.cercat.es.postsearch.entity.PostDocument;
import com.cos.cercat.domain.postsearch.exception.SearchPostNotFoundException;
import com.cos.cercat.domain.post.TargetPost;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostForForSearchRepositoryImpl implements PostForSearchRepository {
    
    private final PostSearchElasticRepository postSearchElasticRepository;

    @Override
    public void save(PostForSearch post) {
        postSearchElasticRepository.save(PostDocument.from(post));
    }

    @Override
    public PostForSearch find(TargetPost targetPost) {
        PostDocument postDocument = postSearchElasticRepository.findById(targetPost.postId()).orElseThrow(
                () -> SearchPostNotFoundException.EXCEPTION);
        return postDocument.toDomain();
    }

    @Override
    public void delete(TargetPost targetPost) {
        postSearchElasticRepository.deleteById(targetPost.postId());
    }

    @Override
    public SliceResult<PostForSearch> search(SearchCond cond,
                                             Certificate certificate,
                                             Cursor cursor) {
        Slice<PostDocument> search = postSearchElasticRepository.search(cond, certificate.id(), toPageRequest(cursor));
        List<PostForSearch> postForSearches = search.getContent().stream()
                .map(PostDocument::toDomain)
                .toList();
        return SliceResult.of(postForSearches, search.hasNext());
    }

    @Override
    public List<String> findAutoCompletedKeywords(Certificate certificate, String searchText) {
        return postSearchElasticRepository.getAutoCompletedKeywords(certificate.id(), searchText);
    }

    @Override
    public List<String> findRecentTrendingKeywords(Certificate certificate) {
        return postSearchElasticRepository.getRecentTop10Keywords(certificate.id());
    }

    private PageRequest toPageRequest(Cursor cursor) {

        Sort sort = Sort.by(cursor.sortOrders().stream()
                .map(order -> new Sort.Order(
                        Sort.Direction.fromOptionalString(order.direction().name()).orElse(Sort.Direction.ASC),
                        order.key()
                ))
                .toList());

        return PageRequest.of(cursor.page(), cursor.size(), sort);
    }
}
