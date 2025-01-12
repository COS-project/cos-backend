package com.cos.cercat.batch.alarm.noop;

import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.post.TargetPost;
import com.cos.cercat.domain.postsearch.PostForSearch;
import com.cos.cercat.domain.postsearch.PostForSearchRepository;
import com.cos.cercat.domain.postsearch.SearchCond;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("batch")
public class NoOPPostForSearchRepository implements PostForSearchRepository {

    @Override
    public void save(PostForSearch post) {

    }

    @Override
    public PostForSearch find(TargetPost targetPost) {
        return null;
    }

    @Override
    public void delete(TargetPost targetPost) {

    }

    @Override
    public SliceResult<PostForSearch> search(SearchCond cond, Certificate certificate,
            Cursor cursor) {
        return null;
    }

    @Override
    public List<String> findAutoCompletedKeywords(Certificate certificate, String searchText) {
        return List.of();
    }

    @Override
    public List<String> findRecentTop10Keywords(Certificate certificate) {
        return List.of();
    }
}
