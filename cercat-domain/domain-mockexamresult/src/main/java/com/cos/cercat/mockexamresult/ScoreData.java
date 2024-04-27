package com.cos.cercat.mockexamresult;

import lombok.AllArgsConstructor;

import java.math.RoundingMode;
import java.text.DecimalFormat;

@AllArgsConstructor
public abstract class ScoreData {
    private final double scoreAverage;

    public double getScoreAverage() {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return Double.parseDouble(df.format(scoreAverage));
    }
}
