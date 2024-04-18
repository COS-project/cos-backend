package com.cos.cercat.domain.user;

import org.springframework.stereotype.Component;

@Component
public class UserReader {

    private final UserRepository userRepository;

    public UserReader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User read(FindUser finduser) {
        return userRepository.read(finduser);
    }
}
