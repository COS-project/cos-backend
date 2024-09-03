package com.cos.cercat.domain.user;

import java.util.Optional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("batch")
public class NoOpCacheRepository implements UserCacheRepository {

    @Override
    public void setUser(User user) {

    }

    @Override
    public Optional<User> getUser(TargetUser targetUser) {
        return Optional.empty();
    }

    @Override
    public void deleteUser(User user) {

    }
}
