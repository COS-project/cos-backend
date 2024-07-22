package com.cos.cercat.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRemover {

    private final UserRepository userRepository;
    private final UserCacheManager userCacheManager;

    public void remove(User user) {
        userRepository.delete(user);
        userCacheManager.remove(user);
    }
}
