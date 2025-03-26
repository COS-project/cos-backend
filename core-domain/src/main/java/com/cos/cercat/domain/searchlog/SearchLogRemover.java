package com.cos.cercat.domain.searchlog;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchLogRemover {

    private final UserSearchLogQueue userSearchLogQueue;


    public void deleteUserSearchHistory(User user, SearchLog searchLog) {
        userSearchLogQueue.pop(user, searchLog);
    }

    public void deleteAllUserSearchHistories(User user, Certificate certificate) {
        userSearchLogQueue.popAll(user, certificate);
    }
}
