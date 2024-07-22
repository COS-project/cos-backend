package com.cos.cercat.common.exception;

public class FileConvertException extends CommonException {

    public static final CustomException EXCEPTION = new FileConvertException();

    private FileConvertException() {
        super(GlobalErrorCode.FILE_CONVERT_ERROR);
    }

}
