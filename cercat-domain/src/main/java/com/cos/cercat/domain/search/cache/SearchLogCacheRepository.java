package com.cos.cercat.domain.search.cache;

import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@Slf4j
@RequiredArgsConstructor
public class SearchLogCacheRepository {

    private final RedisTemplate<String, SearchLog> redisTemplate;

    private final static int LIST_FULL_SIZE = 10;

    public void setLog(String email, SearchLog searchLog) {
        String key = getKey(email);
        log.info("Set Search Log from {} : {}", key, searchLog);

        int size = Objects.requireNonNull(redisTemplate.opsForList().size(key)).intValue();

        if (size == LIST_FULL_SIZE) {
            redisTemplate.opsForList().rightPop(key);
        }

        redisTemplate.opsForList().leftPush(key, searchLog);
    }

    public List<SearchLog> getSearchLogs(String email) {
        String key = getKey(email);
        List<SearchLog> searchLogs = redisTemplate.opsForList().range(key, 0, 10);
        log.info("Get Search Log from {} : {}", key, searchLogs);
        return searchLogs;
    }

    public void deleteLog(String email, SearchLog searchLog) {
        String key = getKey(email);
        int count = Objects.requireNonNull(redisTemplate.opsForList().remove(key, 1, searchLog)).intValue();
        if (count == 0) {
            throw new CustomException(ErrorCode.SEARCH_LOG_NOT_EXIST);
        }
    }

    public void deleteAllLogs(String email) {
        String key = getKey(email);
        redisTemplate.delete(key);
    }

    public String getKey(String email) {
        return "SEARCH_LOG:" + email;
    }

}
