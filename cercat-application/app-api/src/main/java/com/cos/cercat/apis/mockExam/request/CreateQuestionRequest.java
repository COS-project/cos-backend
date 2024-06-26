package com.cos.cercat.apis.mockExam.request;

import java.util.List;
import java.util.Map;

public record CreateQuestionRequest(
        String examYear,
        String round,
        Map<String, List<String>> questions
) {
}
