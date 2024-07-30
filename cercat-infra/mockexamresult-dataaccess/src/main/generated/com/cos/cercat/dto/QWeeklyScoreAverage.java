package com.cos.cercat.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.cos.cercat.dto.QWeeklyScoreAverage is a Querydsl Projection type for WeeklyScoreAverage
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QWeeklyScoreAverage extends ConstructorExpression<WeeklyScoreAverage> {

    private static final long serialVersionUID = -282774289L;

    public QWeeklyScoreAverage(com.querydsl.core.types.Expression<Double> scoreAverage, com.querydsl.core.types.Expression<Integer> weekOfMonth) {
        super(WeeklyScoreAverage.class, new Class<?>[]{double.class, int.class}, scoreAverage, weekOfMonth);
    }

}

