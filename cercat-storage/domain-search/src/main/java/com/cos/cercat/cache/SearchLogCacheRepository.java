package com.cos.cercat.cache;

import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.search.SearchLog;
import com.cos.cercat.domain.search.SearchLogRepository;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@Slf4j
@RequiredArgsConstructor
public class SearchLogCacheRepository implements SearchLogRepository {

    private final RedisTemplate<String, SearchLog> redisTemplate;

    private final static int LIST_FULL_SIZE = 10;

    @Override
    public void setLog(TargetUser targetUser, SearchLog searchLog) {
        String key = getKey(targetUser);
        log.info("Set Search Log from {} : {}", key, searchLog);

        int size = Objects.requireNonNull(redisTemplate.opsForList().size(key)).intValue();

        if (size == LIST_FULL_SIZE) {
            redisTemplate.opsForList().rightPop(key);
        }

        redisTemplate.opsForList().leftPush(key, searchLog);
    }

    @Override
    public List<SearchLog> findSearchLogs(TargetUser targetUser) {
        String key = getKey(targetUser);
        List<SearchLog> searchLogs = redisTemplate.opsForList().range(key, 0, 10);
        log.info("Get Search Log from {} : {}", key, searchLogs);
        return searchLogs;
    }

    @Override
    public void deleteSearchLog(TargetUser targetUser, SearchLog searchLog) {
        String key = getKey(targetUser);
        int count = Objects.requireNonNull(redisTemplate.opsForList().remove(key, 1, searchLog)).intValue();
        if (count == 0) {
            throw new CustomException(ErrorCode.SEARCH_LOG_NOT_EXIST);
        }
    }

    @Override
    public void deleteAllSearchLogs(TargetUser targetUser) {
        String key = getKey(targetUser);
        redisTemplate.delete(key);
    }

    public String getKey(TargetUser targetUser) {
        return "SEARCH_LOG:" + targetUser.userId();
    }

}
