package com.cos.cercat.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCacheManager {

    private final UserCache userCache;

    public void cache(User user) {
        userCache.cache(user);
    }

    public Optional<User> read(UserId userId) {
        return userCache.find(userId);
    }

    public void remove(User user) {
        userCache.delete(user);
    }

    public void refresh(User user) {
        remove(user);
        cache(user);
    }

}
