package com.cos.cercat.mockexamresult;

import java.util.List;

public record MockExamResultDetail(
        MockExamResult mockExamResult,
        List<SubjectResult> subjectResults
) {
}
