package com.cos.cercat.domain.certificate;

import java.util.List;

public record Subjects(
        List<Subject> subjects
) {
    public static Subjects of(List<Subject> subjects) {
        return new Subjects(subjects);
    }
}
