package com.cos.cercat.apis.mockExamResult.response;


import com.cos.cercat.mockexamresult.ScoreData;

import java.util.List;

public record ReportResponse(
        double totalAverage,
        List<? extends ScoreData> scoreAVGList
) {

    public static ReportResponse from(List<? extends ScoreData> scoreAVGList) {
        return new ReportResponse(
                scoreAVGList.stream()
                        .mapToDouble(ScoreData::getScoreAverage)
                        .average()
                        .orElse(0),
                scoreAVGList
        );
    }

}
