package com.cos.cercat.apis.certificate.request;


import com.cos.cercat.certificate.SubjectInfo;

public record SubjectCreateRequest(
        String subjectName,
        Integer numberOfQuestion,
        Integer totalScore
) {
    public SubjectInfo toNewSubject() {
        return new SubjectInfo(
                subjectName,
                numberOfQuestion,
                totalScore
        );
    }

}
