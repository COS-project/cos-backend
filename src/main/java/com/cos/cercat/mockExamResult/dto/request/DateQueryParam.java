package com.cos.cercat.mockExamResult.dto.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public record DateQueryParam(
        Integer year,
        Integer month,
        Integer weekOfMonth,
        @DateTimeFormat(pattern="yyyy-MM-dd") Date date
) {
}
