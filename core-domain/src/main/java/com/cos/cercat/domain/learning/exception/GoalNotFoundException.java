package com.cos.cercat.domain.learning.exception;

import com.cos.cercat.exception.DomainException;
import com.cos.cercat.exception.GlobalErrorCode;
import com.cos.cercat.exception.InfraException;

public class GoalNotFoundException extends DomainException {
    public static final DomainException EXCEPTION = new GoalNotFoundException();

    private GoalNotFoundException() {
        super(LearningErrorCode.GOAL_NOT_FOUND);
    }
}
