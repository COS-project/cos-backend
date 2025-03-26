package com.cos.cercat.domain.user;

import java.util.Optional;

public interface UserCache {
    void cache(User user);

    Optional<User> find(UserId userId);

    void delete(User user);
}
