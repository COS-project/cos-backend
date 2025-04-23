package com.cos.cercat.domain.user;

public interface UserRepository {

  User find(UserId userId);

  User save(User user);

  void delete(User user);

  User readByEmail(String email);

  User readByProvider(Provider provider);
}
