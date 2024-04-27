package com.cos.cercat.user;

public interface UserRepository {

    User read(TargetUser targetUser);

    void update(User user);

    void delete(TargetUser targetUser);

    User save(NewUser newUser);

    User readBy(String email);
}
