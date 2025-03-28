package com.cos.cercat.domain.certificate;

import java.util.Optional;

public interface CertificateCache {

    void cache(Certificate certificate);

    Optional<Certificate> get(CertificateId certificateId);

    void delete(CertificateId certificateId);

}
