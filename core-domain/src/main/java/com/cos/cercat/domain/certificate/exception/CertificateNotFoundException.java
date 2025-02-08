package com.cos.cercat.domain.certificate.exception;

import com.cos.cercat.exception.DomainException;

public class CertificateNotFoundException extends DomainException {
    public static final DomainException EXCEPTION = new CertificateNotFoundException();

    private CertificateNotFoundException() {
        super(CertificateErrorCode.CERTIFICATE_NOT_FOUND);
    }
}
