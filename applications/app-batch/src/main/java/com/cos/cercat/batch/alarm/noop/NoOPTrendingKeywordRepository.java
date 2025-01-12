package com.cos.cercat.batch.alarm.noop;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.postsearch.TrendingKeyword;
import com.cos.cercat.domain.postsearch.TrendingKeywordRepository;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("batch")
public class NoOPTrendingKeywordRepository implements TrendingKeywordRepository {

    @Override
    public void setTrendingKeywords(Certificate certificate,
            List<TrendingKeyword> trendingKeywords) {

    }

    @Override
    public List<TrendingKeyword> findTrendingKeywords(Certificate certificate) {
        return List.of();
    }
}
