package com.cos.cercat.user;

import java.util.Optional;

public interface UserCacheRepository {
    void setUser(User user);

    Optional<User> getUser(TargetUser targetUser);

    void deleteUser(User user);
}
