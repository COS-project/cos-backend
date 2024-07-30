package com.cos.cercat.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReader {

  private final UserRepository userRepository;
  private final UserCacheManager userCacheManager;

  public User read(TargetUser targetUser) {
    return userCacheManager.read(targetUser).orElseGet(() ->
        userRepository.find(targetUser)
    );
  }

  public User readBy(String email) {
    return userRepository.readByEmail(email);
  }
}
