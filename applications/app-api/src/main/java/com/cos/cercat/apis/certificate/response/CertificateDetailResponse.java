package com.cos.cercat.apis.certificate.response;

import com.cos.cercat.domain.certificate.CertificateDetail;
import java.util.List;

public record CertificateDetailResponse(
        Long certificateId,
        String certificateName,
        Integer maxScore,
        List<SubjectResponse> subjects
) {
    public static CertificateDetailResponse from(CertificateDetail certificateDetail) {
        return new CertificateDetailResponse(
                certificateDetail.certificate().id().value(),
                certificateDetail.certificate().certificateName(),
                certificateDetail.subjects().stream()
                        .map(subject -> subject.subjectInfo().totalScore())
                        .reduce(0, Integer::sum),
                certificateDetail.subjects().stream()
                        .map(SubjectResponse::from)
                        .toList()
        );
    }
}
