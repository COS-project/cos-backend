package com.cos.cercat.domain.certificate.exception;


import com.cos.cercat.exception.DomainException;

public class RecentCertificateNotExistException extends DomainException {

    public static final DomainException EXCEPTION = new RecentCertificateNotExistException();

    private RecentCertificateNotExistException() {
        super(CertificateErrorCode.RECENT_CERTIFICATE_EXAM_NOT_EXIST);
    }
}
