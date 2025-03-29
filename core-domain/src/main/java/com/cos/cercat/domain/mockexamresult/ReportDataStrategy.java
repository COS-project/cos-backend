package com.cos.cercat.domain.mockexamresult;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.user.User;

public interface ReportDataStrategy {
    ScoreDataList getScoreData(User user, Certificate certificate, DateCond dateCond);
}
