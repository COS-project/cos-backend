package com.cos.cercat.domain.user;

import java.util.Optional;

public interface UserCacheRepository {
    void setUser(User user);

    Optional<User> getUser(TargetUser targetUser);

    void deleteUser(User user);
}
