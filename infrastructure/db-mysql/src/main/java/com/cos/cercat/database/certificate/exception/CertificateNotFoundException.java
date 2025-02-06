package com.cos.cercat.database.certificate.exception;

import com.cos.cercat.exception.GlobalErrorCode;
import com.cos.cercat.exception.InfraException;

public class CertificateNotFoundException extends InfraException {
    public static final InfraException EXCEPTION = new CertificateNotFoundException();

    private CertificateNotFoundException() {
        super(GlobalErrorCode.CERTIFICATE_NOT_FOUND);
    }
}
