package com.cos.cercat.domain.user;

public interface UserRepository {

  User find(UserId userId);

  User update(User user);

  void delete(User user);

  User save(UserInfo userInfo);

  User readByEmail(String email);
}
