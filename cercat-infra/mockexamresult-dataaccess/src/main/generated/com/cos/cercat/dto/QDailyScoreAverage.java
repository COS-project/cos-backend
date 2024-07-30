package com.cos.cercat.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.cos.cercat.dto.QDailyScoreAverage is a Querydsl Projection type for DailyScoreAverage
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QDailyScoreAverage extends ConstructorExpression<DailyScoreAverage> {

    private static final long serialVersionUID = 7305441L;

    public QDailyScoreAverage(com.querydsl.core.types.Expression<Double> scoreAverage, com.querydsl.core.types.Expression<Integer> dayOfWeek, com.querydsl.core.types.Expression<? extends java.util.Date> date) {
        super(DailyScoreAverage.class, new Class<?>[]{double.class, int.class, java.util.Date.class}, scoreAverage, dayOfWeek, date);
    }

}

