package com.cos.cercat.redis.mockexamresult;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.mockexamresult.DateCond;
import com.cos.cercat.domain.mockexamresult.ReportType;
import com.cos.cercat.domain.mockexamresult.ScoreDataCache;
import com.cos.cercat.domain.mockexamresult.ScoreDataList;
import com.cos.cercat.domain.user.User;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScoreDataRedisCache implements ScoreDataCache {

    private final RedisTemplate<String, ScoreDataList> redisTemplate;

    @Override
    public void cache(
            ReportType reportType,
            User user,
            Certificate certificate,
            ScoreDataList scoreData,
            DateCond dateCond
    ) {
        String key = getKey(reportType, user, certificate, dateCond);
        redisTemplate.opsForValue().setIfAbsent(key, scoreData);
    }

    @Override
    public Optional<ScoreDataList> get(
            ReportType reportType,
            User user,
            Certificate certificate,
            DateCond dateCond
    ) {
        String key = getKey(reportType, user, certificate, dateCond);
        ScoreDataList scoreData = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(scoreData);
    }

    @Override
    public void delete(User user, Certificate certificate) {
        String keyPattern = String.format("SCORE_DATA:*:%s:%s:*", user.getId(), certificate.id().value());
        Set<String> keys = redisTemplate.keys(keyPattern);

        if (!keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    private String getKey(
            ReportType reportType,
            User user,
            Certificate certificate,
            DateCond dateCond
    ) {
        String dateCondKey = switch (reportType) {
            case WEEKLY -> String.format("%d-%d-%d", dateCond.year(), dateCond.month(), dateCond.weekOfMonth());
            case MONTHLY -> String.format("%d-%d", dateCond.year(), dateCond.month());
            case YEARLY -> String.valueOf(dateCond.year());
        };

        return String.format("SCORE_DATA:%s:%s:%s:%s",
                reportType,
                user.getId(),
                certificate.id().value(),
                dateCondKey
        );
    }

}
