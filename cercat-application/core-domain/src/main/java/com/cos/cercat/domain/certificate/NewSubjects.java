package com.cos.cercat.domain.certificate;

import java.util.List;

public record NewSubjects(
        List<NewSubject> subjectList
) {
    public static NewSubjects of(List<NewSubject> newSubjects) {
        return new NewSubjects(newSubjects);
    }
}
