package com.cos.cercat.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReader {

    private final UserRepository userRepository;
    private final UserCacheManger userCacheManger;

    public User read(TargetUser targetUser) {
        return userCacheManger.read(targetUser).orElseGet( () ->
                userRepository.read(targetUser)
        );
    }

    public User readBy(String email) {
        return userRepository.readBy(email);
    }

    public boolean isLoginUser(TargetUser targetUser, String token) {
        return false;
    }
}
