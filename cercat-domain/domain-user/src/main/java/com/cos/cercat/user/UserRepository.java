package com.cos.cercat.user;

public interface UserRepository {

    User read(TargetUser targetUser);

    User update(User user);

    void delete(User user);

    User save(UserInfo userInfo);

    User readBy(String email);
}
