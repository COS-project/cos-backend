package com.cos.cercat.search;


import com.cos.cercat.user.TargetUser;

import java.util.List;

public interface SearchLogRepository {


    void setLog(TargetUser targetUser, SearchLog searchLog);

    List<SearchLog> findSearchLogs(TargetUser targetUser);

    void deleteSearchLog(TargetUser targetUser, SearchLog searchLog);

    void deleteAllSearchLogs(TargetUser targetUser);

}
