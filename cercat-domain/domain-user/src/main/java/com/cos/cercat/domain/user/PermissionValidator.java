package com.cos.cercat.domain.user;

import com.cos.cercat.domain.exception.NoPermissionException;
import org.springframework.stereotype.Component;

@Component
public class PermissionValidator {

    public void validate(Ownable ownable, User user) {
        if (!ownable.isOwner(user)) {
            throw NoPermissionException.EXCEPTION;
        }
    }

}
