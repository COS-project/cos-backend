package com.cos.cercat.domain.mockexamresult;

import java.util.List;

public record MockExamResultDetail(
        MockExamResult mockExamResult,
        List<SubjectResult> subjectResults
) {
}
