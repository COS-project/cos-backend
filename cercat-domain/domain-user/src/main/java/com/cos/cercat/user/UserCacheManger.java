package com.cos.cercat.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCacheManger {

    private final UserCacheRepository userCacheRepository;

    public void append(User user) {
        userCacheRepository.setUser(user);
    }

    public Optional<User> read(TargetUser targetUser) {
        return userCacheRepository.getUser(targetUser);
    }

    public void remove(TargetUser targetUser) {
        userCacheRepository.deleteUser(targetUser);
    }

    public void refresh(User user) {
        remove(TargetUser.from(user.getId()));
        append(user);
    }

}
