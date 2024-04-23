package com.cos.cercat.repository;

import com.cos.cercat.cache.SearchLogCacheRepository;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.PostDocument;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.post.TargetPost;
import com.cos.cercat.domain.search.PostForSearch;
import com.cos.cercat.domain.search.PostForSearchRepository;
import com.cos.cercat.domain.search.SearchCond;
import com.cos.cercat.domain.search.SearchLog;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostForForSearchRepositoryImpl implements PostForSearchRepository {
    
    private final PostSearchElasticRepository postSearchElasticRepository;
    private final SearchLogCacheRepository searchLogCacheRepository;

    @Override
    public void save(PostForSearch post) {
        postSearchElasticRepository.save(PostDocument.from(post));
    }

    @Override
    public PostForSearch find(TargetPost targetPost) {
        PostDocument postDocument = postSearchElasticRepository.findById(targetPost.postId()).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND));
        return postDocument.toDomain();
    }

    @Override
    public void delete(TargetPost targetPost) {
        postSearchElasticRepository.deleteById(targetPost.postId());
    }

    @Override
    public SliceResult<PostForSearch> search(SearchCond cond, TargetCertificate targetCertificate, Cursor cursor) {
        Slice<PostDocument> search = postSearchElasticRepository.search(cond, targetCertificate.certificateId(), toPageRequest(cursor));
        List<PostForSearch> postForSearches = search.getContent().stream()
                .map(PostDocument::toDomain)
                .toList();
        return SliceResult.of(postForSearches, search.hasNext());
    }

    @Override
    public List<String> findAutoCompletedKeywords(TargetCertificate targetCertificate, String searchText) {
        return postSearchElasticRepository.getAutoCompletedKeywords(targetCertificate.certificateId(), searchText);
    }

    @Override
    public List<String> findRecentTop5Keywords(TargetCertificate targetCertificate) {
        return postSearchElasticRepository.getRecentTop5Keywords(targetCertificate.certificateId());
    }

    private PageRequest toPageRequest(Cursor cursor) {
        Sort.Direction direction = Sort.Direction.fromOptionalString(cursor.sortDirection().name()).orElseThrow();
        return PageRequest.of(cursor.page(), cursor.limit(), Sort.by(direction, cursor.sortKey().key()));
    }
}
