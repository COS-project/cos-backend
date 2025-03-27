package com.cos.cercat.redis.searchlog;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.searchlog.SearchLog;
import com.cos.cercat.domain.searchlog.UserSearchLogQueue;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class RedisUserSearchLogQueue implements UserSearchLogQueue {

    private final StringRedisTemplate redisTemplate;

    private final static int LIST_FULL_SIZE = 10;

    @Override
    public void push(User user, SearchLog searchLog) {
        String key = getKey(user.getId(), searchLog.certificateId().value());
        log.info("Set Search Log from {} : {}", key, searchLog);
        redisTemplate.opsForList().leftPush(key, searchLog.keyword());
        redisTemplate.opsForList().trim(key, 0, LIST_FULL_SIZE - 1); // 리스트 크기를 10으로 유지
    }

    @Override
    public List<String> getAll(User user, Certificate certificate) {
        String key = getKey(user.getId(), certificate.id().value());
        List<String> searchLogs = redisTemplate.opsForList().range(key, 0, 10);
        log.info("Get Search Log from {} : {}", key, searchLogs);
        return searchLogs;
    }

    @Override
    public void pop(User user, SearchLog searchLog) {
        String key = getKey(user.getId(), searchLog.certificateId().value());
        redisTemplate.opsForList().remove(key, 1, searchLog.keyword());
    }

    @Override
    public void popAll(User user, Certificate certificate) {
        String key = getKey(user.getId(), certificate.id().value());
        redisTemplate.delete(key);
    }

    @Override
    public boolean exists(User user, SearchLog searchLog) {
        String key = getKey(user.getId(), searchLog.certificateId().value());
        List<String> searchLogs = redisTemplate.opsForList().range(key, 0, 10);

        if (searchLogs == null) {
            return false;
        }

        return searchLogs.contains(searchLog.keyword());
    }

    public String getKey(Long userId, Long certificateId) {
        return "SEARCH_LOG:" + certificateId + ":" + userId;
    }

}
