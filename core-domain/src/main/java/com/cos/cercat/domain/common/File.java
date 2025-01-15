package com.cos.cercat.domain.common;

import java.io.InputStream;

public record File(
        String contentType,
        InputStream inputStream
) {

}
