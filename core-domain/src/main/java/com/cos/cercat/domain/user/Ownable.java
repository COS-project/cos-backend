package com.cos.cercat.domain.user;

public interface Ownable {

    User getOwner();

    default boolean isOwner(User user) {
        return getOwner().getId().equals(user.getId());
    }
}
