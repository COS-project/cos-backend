package com.cos.cercat.redis.postsearch;

import com.cos.cercat.domain.postsearch.SearchLog;
import com.cos.cercat.domain.postsearch.SearchLogQueue;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class RedisSearchLogQueue implements SearchLogQueue {

    private final RedisTemplate<String, SearchLog> redisTemplate;

    private final static int LIST_FULL_SIZE = 10;

    @Override
    public void push(User user, SearchLog searchLog) {
        String key = getKey(user.getId());
        log.info("Set Search Log from {} : {}", key, searchLog);
        redisTemplate.opsForList().leftPush(key, searchLog);
        redisTemplate.opsForList().trim(key, 0, LIST_FULL_SIZE - 1); // 리스트 크기를 10으로 유지
    }

    @Override
    public List<SearchLog> getAll(User user) {
        String key = getKey(user.getId());
        List<SearchLog> searchLogs = redisTemplate.opsForList().range(key, 0, 10);
        log.info("Get Search Log from {} : {}", key, searchLogs);
        return searchLogs;
    }

    @Override
    public void pop(User user, SearchLog searchLog) {
        String key = getKey(user.getId());
        redisTemplate.opsForList().remove(key, 1, searchLog);
    }

    @Override
    public void popAll(User user) {
        String key = getKey(user.getId());
        redisTemplate.delete(key);
    }

    @Override
    public boolean exists(User user, SearchLog searchLog) {
        String key = getKey(user.getId());
        List<SearchLog> searchLogs = redisTemplate.opsForList().range(key, 0, 10);

        if (searchLogs == null) {
            return false;
        }

        return searchLogs.contains(searchLog);
    }

    public String getKey(Long userId) {
        return "SEARCH_LOG:" + userId;
    }

}
