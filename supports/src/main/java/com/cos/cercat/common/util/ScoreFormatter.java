package com.cos.cercat.common.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class ScoreFormatter {
    public static double formatScore(double score) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return Double.parseDouble(df.format(score));
    }
}
