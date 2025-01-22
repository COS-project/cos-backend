package com.cos.cercat.domain.user;

public interface Ownable {

    User getUser();

    default boolean isOwner(User user) {
        return getUser().getId().equals(user.getId());
    }
}
