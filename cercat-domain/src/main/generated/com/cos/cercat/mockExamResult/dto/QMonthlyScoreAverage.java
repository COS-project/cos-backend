package com.cos.cercat.mockExamResult.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.cos.cercat.mockExamResult.dto.QMonthlyScoreAverage is a Querydsl Projection type for MonthlyScoreAverage
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMonthlyScoreAverage extends ConstructorExpression<MonthlyScoreAverage> {

    private static final long serialVersionUID = -1460013163L;

    public QMonthlyScoreAverage(com.querydsl.core.types.Expression<Double> scoreAverage, com.querydsl.core.types.Expression<Integer> month) {
        super(MonthlyScoreAverage.class, new Class<?>[]{double.class, int.class}, scoreAverage, month);
    }

}

