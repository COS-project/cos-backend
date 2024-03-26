package com.cos.cercat.domain.mockExamResult.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.cos.cercat.domain.mockExamResult.dto.QWeeklyScoreAverage is a Querydsl Projection type for WeeklyScoreAverage
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QWeeklyScoreAverage extends ConstructorExpression<WeeklyScoreAverage> {

    private static final long serialVersionUID = -1909553939L;

    public QWeeklyScoreAverage(com.querydsl.core.types.Expression<Double> scoreAverage, com.querydsl.core.types.Expression<Integer> weekOfMonth) {
        super(WeeklyScoreAverage.class, new Class<?>[]{double.class, int.class}, scoreAverage, weekOfMonth);
    }

}

