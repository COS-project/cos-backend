package com.cos.cercat.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReader {

  private final UserRepository userRepository;
  private final UserCacheManager userCacheManager;

  public User read(UserId userId) {
    return userCacheManager.read(userId).orElseGet(() ->
        userRepository.find(userId)
    );
  }

  public User read(String email) {
    return userRepository.readByEmail(email);
  }

  public User read(Provider provider) {
    return userRepository.readByProvider(provider);
  }


}
