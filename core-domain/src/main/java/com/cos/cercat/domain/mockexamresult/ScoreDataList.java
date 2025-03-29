package com.cos.cercat.domain.mockexamresult;

import java.util.List;

public record ScoreDataList(
        int maxScore,
        double scoreAverage,
        List<ScoreData> dataList
) {

}
