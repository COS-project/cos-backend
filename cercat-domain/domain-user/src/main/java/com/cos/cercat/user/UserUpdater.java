package com.cos.cercat.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUpdater {

    private final UserRepository userRepository;
    private final UserCacheManger userCacheManger;

    public void update(User user) {
        userRepository.update(user);
        userCacheManger.refresh(user);
    }
}
