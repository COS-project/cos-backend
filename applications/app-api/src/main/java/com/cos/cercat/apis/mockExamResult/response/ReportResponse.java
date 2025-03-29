package com.cos.cercat.apis.mockExamResult.response;


import com.cos.cercat.domain.mockexamresult.ScoreData;

import com.cos.cercat.domain.mockexamresult.ScoreDataList;
import java.util.List;

public record ReportResponse(
        int maxScore,
        double totalAverage,
        List<? extends ScoreData> scoreAVGList
) {

    public static ReportResponse from(ScoreDataList scoreDataList) {
        return new ReportResponse(
                scoreDataList.maxScore(),
                scoreDataList.scoreAverage(),
                scoreDataList.dataList()
        );
    }

}
