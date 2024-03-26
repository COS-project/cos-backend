package com.cos.cercat.domain.mockExamResult.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubjectResults {

    @OneToMany(mappedBy = "mockExamResult", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SubjectResult> subjectResults = new ArrayList<>();

    public void addAll(List<SubjectResult> subjectResults) {
        this.subjectResults.addAll(subjectResults);
    }

    private SubjectResults(List<SubjectResult> subjectResults) {
        this.subjectResults = subjectResults;
    }

    public static SubjectResults from(List<SubjectResult> subjectResults) {
        return new SubjectResults(
                subjectResults
        );
    }
}
