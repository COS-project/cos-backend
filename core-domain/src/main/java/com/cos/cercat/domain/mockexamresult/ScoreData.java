package com.cos.cercat.domain.mockexamresult;

import lombok.AllArgsConstructor;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public abstract class ScoreData {
    private int maxScore;
    private double scoreAverage;

    public double getScoreAverage() {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return Double.parseDouble(df.format(scoreAverage));
    }
}
