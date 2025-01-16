package com.cos.cercat.domain.user;

import java.util.Optional;

public interface UserCache {
    void cache(User user);

    Optional<User> find(TargetUser targetUser);

    void delete(User user);
}
