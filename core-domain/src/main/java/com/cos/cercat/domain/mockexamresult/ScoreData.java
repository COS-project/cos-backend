package com.cos.cercat.domain.mockexamresult;

import lombok.AllArgsConstructor;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public abstract class ScoreData {
    private double scoreAverage;

    public double getScoreAverage() {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return Double.parseDouble(df.format(scoreAverage));
    }
}
