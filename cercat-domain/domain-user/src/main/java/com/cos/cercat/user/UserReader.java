package com.cos.cercat.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReader {

    private final UserRepository userRepository;
    private final UserCacheManager userCacheManager;

    public User read(TargetUser targetUser) {
        return userCacheManager.read(targetUser).orElseGet( () ->
                userRepository.read(targetUser)
        );
    }

    public User readBy(String email) {
        return userRepository.readBy(email);
    }
}
