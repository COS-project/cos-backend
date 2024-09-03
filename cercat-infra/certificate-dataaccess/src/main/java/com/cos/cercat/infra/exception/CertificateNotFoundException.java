package com.cos.cercat.infra.exception;

import com.cos.cercat.common.exception.GlobalErrorCode;
import com.cos.cercat.common.exception.InfraException;

public class CertificateNotFoundException extends InfraException {
    public static final InfraException EXCEPTION = new CertificateNotFoundException();

    private CertificateNotFoundException() {
        super(GlobalErrorCode.CERTIFICATE_NOT_FOUND);
    }
}
