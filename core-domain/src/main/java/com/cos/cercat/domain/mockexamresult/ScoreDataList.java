package com.cos.cercat.domain.mockexamresult;

import java.util.List;

public record ScoreDataList(
        List<ScoreData> dataList
) {
    public static ScoreDataList from(List<ScoreData> scoreDataList) {
        return new ScoreDataList(scoreDataList);
    }
}
