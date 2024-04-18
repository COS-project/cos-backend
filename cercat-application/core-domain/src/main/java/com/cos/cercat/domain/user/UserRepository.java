package com.cos.cercat.domain.user;

import org.springframework.stereotype.Component;

public interface UserRepository {

    User read(FindUser findUser);

}
