package com.cos.cercat.domain.certificate.exception;

import com.cos.cercat.exception.DomainException;

public class CertificateException extends DomainException {

    private CertificateException(CertificateErrorCode errorCode) {
        super(errorCode);
    }

    private CertificateException(CertificateErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    // 기본 팩토리 메소드
    public static CertificateException notFound() {
        return new CertificateException(CertificateErrorCode.CERTIFICATE_NOT_FOUND);
    }

    public static CertificateException notFound(String message) {
        return new CertificateException(CertificateErrorCode.CERTIFICATE_NOT_FOUND, message);
    }

    public static CertificateException notFoundExam(String message) {
        return new CertificateException(CertificateErrorCode.NOT_FOUND_CERTIFICATE_EXAM, message);
    }
}
