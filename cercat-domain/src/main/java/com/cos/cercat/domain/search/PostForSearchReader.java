package com.cos.cercat.domain.search;

import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostForSearchReader {
    
    private final PostForSearchRepository postForSearchRepository;

    public SliceResult<PostForSearch> read(SearchCond cond, TargetCertificate targetCertificate, Cursor cursor) {
        return postForSearchRepository.search(cond, targetCertificate, cursor);
    }

    public List<String> readAutoCompletedKeywords(TargetCertificate targetCertificate, String searchText) {
        return postForSearchRepository.findAutoCompletedKeywords(targetCertificate, searchText);
    }

    public List<String> readRecentTop5Keywords(TargetCertificate targetCertificate) {
        return postForSearchRepository.findRecentTop5Keywords(targetCertificate);
    }
}
