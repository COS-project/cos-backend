package com.cos.cercat.batch.alarm.noop;

import com.cos.cercat.domain.postsearch.SearchLog;
import com.cos.cercat.domain.postsearch.SearchLogRepository;
import com.cos.cercat.domain.user.User;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("batch")
public class NoOPSearchLogRepository implements SearchLogRepository {

    @Override
    public void setLog(User user, SearchLog searchLog) {

    }

    @Override
    public List<SearchLog> findSearchLogs(User user) {
        return List.of();
    }

    @Override
    public void deleteSearchLog(User user, SearchLog searchLog) {

    }

    @Override
    public void deleteAllSearchLogs(User user) {

    }
}
