package com.cos.cercat.domain.search;

import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SliceResult;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.post.Post;
import com.cos.cercat.domain.post.PostReader;
import com.cos.cercat.domain.post.TargetPost;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchPostService {

    private final PostReader postReader;
    private final PostForSearchReader postForSearchReader;
    private final SearchLogAppender searchLogAppender;
    private final SearchLogRemover searchLogRemover;
    private final SearchLogReader searchLogReader;

    public SliceResult<Post> search(TargetUser targetUser,
                                    TargetCertificate targetCertificate,
                                    SearchCond cond,
                                    Cursor cursor) {
        if (!cond.keyword().isBlank()){
            searchLogAppender.appendSearchLog(targetUser, cond.keyword());
        }
        return postForSearchReader.read(cond, targetCertificate, cursor)
                .map(post -> postReader.read(TargetPost.from(post.getId())));
    }

    public List<String> readAutoCompleteKeyword(TargetCertificate targetCertificate, String searchText) {
        return postForSearchReader.readAutoCompletedKeywords(targetCertificate, searchText);
    }

    public List<String> getRecentTop5Keywords(TargetCertificate targetCertificate) {
        return postForSearchReader.readRecentTop5Keywords(targetCertificate);
    }

    public List<SearchLog> readSearchLogs(TargetUser targetUser) {
        return searchLogReader.readSearchLogs(targetUser);
    }

    public void deleteSearchLog(TargetUser targetUser, SearchLog searchLog) {
        searchLogRemover.deleteSearchLog(targetUser, searchLog);
    }

    public void deleteAllSearchLogs(TargetUser targetUser) {
        searchLogRemover.deleteAllSearchLogs(targetUser);
    }
    
    
}
