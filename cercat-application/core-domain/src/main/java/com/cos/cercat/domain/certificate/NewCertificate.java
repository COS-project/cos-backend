package com.cos.cercat.domain.certificate;

import java.util.List;

public record NewCertificate(
        String certificateName,
        NewSubjects newSubjects
) {
    public List<NewSubject> getSubjectList() {
        return newSubjects.subjectList();
    }
}
