package com.cos.cercat.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCacheManager {

    private final UserCacheRepository userCacheRepository;

    public void cache(User user) {
        userCacheRepository.setUser(user);
    }

    public Optional<User> read(TargetUser targetUser) {
        return userCacheRepository.getUser(targetUser);
    }

    public void remove(User user) {
        userCacheRepository.deleteUser(user);
    }

    public void refresh(User user) {
        remove(user);
        cache(user);
    }

}
