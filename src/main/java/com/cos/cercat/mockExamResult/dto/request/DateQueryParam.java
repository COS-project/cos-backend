package com.cos.cercat.mockExamResult.dto.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public record DateQueryParam(
        @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
        Integer weekOfMonth,
        Integer month
) {
}
