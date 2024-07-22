package com.cos.cercat.exception;

import com.cos.cercat.common.exception.GlobalErrorCode;
import com.cos.cercat.common.exception.InfraException;

public class GoalNotFoundException extends InfraException {
    public static final InfraException EXCEPTION = new GoalNotFoundException();

    private GoalNotFoundException() {
        super(GlobalErrorCode.GOAL_NOT_FOUND);
    }
}
