package com.cos.cercat.mockExamResult.dto;

import lombok.AllArgsConstructor;

import java.math.RoundingMode;
import java.text.DecimalFormat;


@AllArgsConstructor
public class ScoreAverage {
    private final double scoreAverage;

    public double getScoreAverage() {
        // 소수점 2자리까지 반올림
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return Double.parseDouble(df.format(scoreAverage));
    }
}
