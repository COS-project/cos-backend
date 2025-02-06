package com.cos.cercat.redis.like;

import com.cos.cercat.domain.like.LikeCount;
import com.cos.cercat.domain.like.LikeCountBuffer;
import com.cos.cercat.domain.like.LikeTarget;
import com.cos.cercat.domain.like.LikeTargetType;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class LikeRedisCache implements LikeCountBuffer {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void countUp(LikeTarget likeTarget) {
        String countKey = getKey(likeTarget);
        Long incrementResult = redisTemplate.opsForValue().increment(countKey);
        if (incrementResult != null) {
            redisTemplate.expire(countKey, Duration.ofDays(1));
        } else {
            throw new IllegalStateException("좋아요 수 증가 실패");
        }
    }

    @Override
    public void countDown(LikeTarget likeTarget) {
        String countKey = getKey(likeTarget);
        Long decrementResult = redisTemplate.opsForValue().decrement(countKey);
        if (decrementResult != null) {
            redisTemplate.expire(countKey, Duration.ofDays(1));
        } else {
            throw new IllegalStateException("좋아요 수 감소 실패");
        }
    }

    @Override
    public List<LikeCount> getAll() {
        ScanOptions scanOptions = ScanOptions.scanOptions()
                .match("*:LIKE:*")
                .count(1000)
                .build();

        List<LikeCount> counts = new ArrayList<>();

        redisTemplate.execute((RedisCallback<Object>) connection -> {
            try (Cursor<byte[]> cursor = connection.commands().scan(scanOptions)) {
                cursor.forEachRemaining(key -> {
                    String keyStr = new String(key, StandardCharsets.UTF_8);
                    try {
                        LikeTarget likeTarget = parseLikeTarget(keyStr);
                        String value = redisTemplate.opsForValue().getAndDelete(keyStr);
                        Long count = value != null ? Long.parseLong(value) : 0L;
                        counts.add(new LikeCount(likeTarget, count));
                    } catch (IllegalArgumentException e) {
                        log.error("Invalid key format: {}", keyStr);
                    }
                });
            } catch (Exception e) {
                throw new RuntimeException("Error during Redis scan", e);
            }
            return null;
        });
        return counts;
    }

    private String getKey(LikeTarget likeTarget) {
        return likeTarget.targetType() + ":LIKE:" + likeTarget.targetId();
    }

    private LikeTarget parseLikeTarget(String key) {
        String[] parts = key.split(":");
        Long targetId = Long.parseLong(parts[2]);
        LikeTargetType targetType = LikeTargetType.valueOf(parts[0]);
        return new LikeTarget(targetId, targetType);

    }
}
