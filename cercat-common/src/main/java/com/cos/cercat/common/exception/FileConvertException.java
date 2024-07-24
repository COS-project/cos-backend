package com.cos.cercat.common.exception;

public class FileConvertException extends CustomException {

    public static final CustomException EXCEPTION = new FileConvertException();

    private FileConvertException() {
        super(GlobalErrorCode.FILE_CONVERT_ERROR);
    }

}
