package com.cos.cercat.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRemover {

    private final UserRepository userRepository;
    private final UserCacheManger userCacheManger;

    public void remove(TargetUser targetUser) {
        userRepository.delete(targetUser);
        userCacheManger.remove(targetUser);
    }
}
