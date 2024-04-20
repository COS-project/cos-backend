package com.cos.cercat.domain.certificate;

public record PassingCriteria(
        Integer subjectPassingCriteria,
        Integer totalAvgCriteria,
        Integer practicalPassingCriteria
) {
    public static PassingCriteria of(Integer subjectPassingCriteria, Integer totalAvgCriteria, Integer practicalPassingCriteria) {
        return new PassingCriteria(subjectPassingCriteria, totalAvgCriteria, practicalPassingCriteria);
    }
}