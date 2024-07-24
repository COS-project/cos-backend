package com.cos.cercat.domain.user;

public interface Ownable {
    boolean isOwner(User user);
}
