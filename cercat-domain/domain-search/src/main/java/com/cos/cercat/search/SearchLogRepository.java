package com.cos.cercat.search;


import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;

import java.util.List;

public interface SearchLogRepository {

    void setLog(User user, SearchLog searchLog);

    List<SearchLog> findSearchLogs(User user);

    void deleteSearchLog(User user, SearchLog searchLog);

    void deleteAllSearchLogs(User user);

}
