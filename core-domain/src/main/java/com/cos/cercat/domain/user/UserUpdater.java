package com.cos.cercat.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUpdater {

    private final UserRepository userRepository;
    private final UserCacheManager userCacheManager;

    public User update(User user) {
        userCacheManager.refresh(user);
        return userRepository.save(user);
    }
}
