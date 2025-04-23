package com.cos.cercat.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAppender {

    private final UserRepository userRepository;

    public User append(UserInfo userInfo) {
        return userRepository.save(User.create(userInfo));
    }
}
