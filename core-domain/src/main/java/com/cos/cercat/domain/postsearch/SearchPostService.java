package com.cos.cercat.domain.postsearch;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateReader;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.common.Cursor;
import com.cos.cercat.domain.common.SliceResult;
import com.cos.cercat.domain.post.Post;
import com.cos.cercat.domain.post.PostReader;
import com.cos.cercat.domain.post.TargetPost;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchPostService {

    private final PostReader postReader;
    private final CertificateReader certificateReader;
    private final UserReader userReader;
    private final PostForSearchReader postForSearchReader;
    private final SearchLogAppender searchLogAppender;
    private final SearchLogRemover searchLogRemover;
    private final SearchLogReader searchLogReader;

    public SliceResult<Post> search(TargetUser targetUser,
                                    TargetCertificate targetCertificate,
                                    SearchCond cond,
                                    Cursor cursor) {
        User user = userReader.read(targetUser);
        Certificate certificate = certificateReader.read(targetCertificate);
        searchLogAppender.append(user, cond.keyword());
        return postForSearchReader.read(cond, certificate, cursor)
                .map(post -> postReader.read(TargetPost.from(post.getId())));
    }

    public List<String> readAutoCompleteKeyword(TargetCertificate targetCertificate, String searchText) {
        Certificate certificate = certificateReader.read(targetCertificate);
        return postForSearchReader.readAutoCompletedKeywords(certificate, searchText);
    }

    public List<TrendingKeyword> getTrendingKeywords(TargetCertificate targetCertificate) {
        Certificate certificate = certificateReader.read(targetCertificate);
        return postForSearchReader.readTrendingKeywords(certificate);
    }

    public List<SearchLog> readSearchLogs(TargetUser targetUser) {
        User user = userReader.read(targetUser);
        return searchLogReader.readSearchLogs(user);
    }

    public void deleteSearchLog(TargetUser targetUser, SearchLog searchLog) {
        User user = userReader.read(targetUser);
        searchLogRemover.deleteSearchLog(user, searchLog);
    }

    public void deleteAllSearchLogs(TargetUser targetUser) {
        User user = userReader.read(targetUser);
        searchLogRemover.deleteAllSearchLogs(user);
    }
    
    
}
