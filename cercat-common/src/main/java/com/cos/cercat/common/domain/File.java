package com.cos.cercat.common.domain;

import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public record File(
        String contentType,
        InputStream inputStream
) {
    public static File from(MultipartFile multipartFile) {
        try {

            if (multipartFile == null) {
                return null;
            }

            return new File(
                    multipartFile.getContentType(),
                    multipartFile.getInputStream()
            );
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_CONVERT_ERROR);
        }
    }
}
