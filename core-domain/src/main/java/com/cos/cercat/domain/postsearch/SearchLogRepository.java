package com.cos.cercat.domain.postsearch;


import com.cos.cercat.domain.user.User;

import java.util.List;

public interface SearchLogRepository {

    void setLog(User user, SearchLog searchLog);

    List<SearchLog> findSearchLogs(User user);

    void deleteSearchLog(User user, SearchLog searchLog);

    void deleteAllSearchLogs(User user);

}
