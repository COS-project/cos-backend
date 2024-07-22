package com.cos.cercat.user;

import com.cos.cercat.exception.NoPermissionException;
import org.springframework.stereotype.Component;

@Component
public class PermissionValidator {

    public void validate(Ownable ownable, User user) {
        if (!ownable.isOwner(user)) {
            throw NoPermissionException.EXCEPTION;
        }
    }

}
