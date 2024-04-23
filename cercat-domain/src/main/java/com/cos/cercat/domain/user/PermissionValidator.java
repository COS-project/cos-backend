package com.cos.cercat.domain.user;

import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class PermissionValidator {

    public void validate(Ownable ownable, User user) {
        if (!ownable.isOwner(user)) {
            throw new CustomException(ErrorCode.NO_PERMISSION_ERROR);
        }
    }

}
