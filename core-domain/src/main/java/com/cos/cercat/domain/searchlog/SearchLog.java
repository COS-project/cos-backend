package com.cos.cercat.domain.searchlog;

import com.cos.cercat.domain.certificate.CertificateId;

public record SearchLog(
        CertificateId certificateId,
        String keyword
) {
    public static SearchLog of(Long certificateId, String keyword) {
        return new SearchLog(CertificateId.from(certificateId), keyword);
    }

    public boolean notValid() {
        return keyword == null || keyword.isBlank();
    }
}
