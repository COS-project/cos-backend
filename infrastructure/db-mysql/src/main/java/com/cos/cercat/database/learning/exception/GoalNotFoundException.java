package com.cos.cercat.database.learning.exception;

import com.cos.cercat.exception.GlobalErrorCode;
import com.cos.cercat.exception.InfraException;

public class GoalNotFoundException extends InfraException {
    public static final InfraException EXCEPTION = new GoalNotFoundException();

    private GoalNotFoundException() {
        super(GlobalErrorCode.GOAL_NOT_FOUND);
    }
}
