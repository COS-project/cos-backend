package com.cos.cercat.domain.user;

public interface UserRepository {

    User read(TargetUser targetUser);

    User update(User user);

    void delete(User user);

    User save(UserInfo userInfo);

    User readBy(String email);
}
