package com.cos.cercat.domain.mockexamresult;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.user.User;
import java.util.Optional;

public interface ScoreDataCache {

    void cache(
            ReportType reportType,
            User user,
            Certificate certificate,
            ScoreDataList scoreDataList,
            DateCond dateCond
    );

    Optional<ScoreDataList> get(
            ReportType reportType,
            User user,
            Certificate certificate,
            DateCond dateCond
    );

    void delete(
            User user,
            Certificate certificate
    );

}
