package com.cos.cercat.gcs.exception;

import com.cos.cercat.exception.InfraException;

public class ImageUploadException extends InfraException {

    public static final InfraException EXCEPTION = new ImageUploadException();

    private ImageUploadException() {
        super(GcsErrorCode.IMAGE_UPLOAD_FAIL_ERROR);
    }
}
