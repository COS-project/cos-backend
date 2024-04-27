package com.cos.cercat.cache;

import com.cos.cercat.user.BanTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class BanTokenRepositoryImpl implements BanTokenRepository {

    private final static String BAN_TOKEN_KEY = "BAN_TOKEN";

    private final RedisTemplate<String, String> banTokenRedisTemplate;

    public void ban(String accessToken) {
        banTokenRedisTemplate.opsForSet().add(BAN_TOKEN_KEY, accessToken);
    }

    public boolean isLoginUser(String accessToken) {
        return Boolean.FALSE.equals(banTokenRedisTemplate.opsForSet().isMember(BAN_TOKEN_KEY, accessToken));

    }


}
