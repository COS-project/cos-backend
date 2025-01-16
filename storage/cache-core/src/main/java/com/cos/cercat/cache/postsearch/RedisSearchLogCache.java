package com.cos.cercat.cache.postsearch;

import com.cos.cercat.cache.postsearch.exception.SearchLogNotExistException;
import com.cos.cercat.domain.postsearch.SearchLog;
import com.cos.cercat.domain.postsearch.SearchLogCache;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@Slf4j
@RequiredArgsConstructor
public class RedisSearchLogCache implements SearchLogCache {

    private final RedisTemplate<String, SearchLog> redisTemplate;

    private final static int LIST_FULL_SIZE = 10;

    @Override
    public void cache(User user, SearchLog searchLog) {
        String key = getKey(user.getId());
        log.info("Set Search Log from {} : {}", key, searchLog);

        int size = Objects.requireNonNull(redisTemplate.opsForList().size(key)).intValue();

        if (size == LIST_FULL_SIZE) {
            redisTemplate.opsForList().rightPop(key);
        }

        redisTemplate.opsForList().leftPush(key, searchLog);
    }

    @Override
    public List<SearchLog> get(User user) {
        String key = getKey(user.getId());
        List<SearchLog> searchLogs = redisTemplate.opsForList().range(key, 0, 10);
        log.info("Get Search Log from {} : {}", key, searchLogs);
        return searchLogs;
    }

    @Override
    public void delete(User user, SearchLog searchLog) {
        String key = getKey(user.getId());
        int count = Objects.requireNonNull(redisTemplate.opsForList().remove(key, 1, searchLog)).intValue();
        if (count == 0) {
            throw SearchLogNotExistException.EXCEPTION;
        }
    }

    @Override
    public void deleteAll(User user) {
        String key = getKey(user.getId());
        redisTemplate.delete(key);
    }

    public String getKey(Long userId) {
        return "SEARCH_LOG:" + userId;
    }

}
