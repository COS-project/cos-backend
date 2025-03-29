package com.cos.cercat.domain.mockexam;

import com.cos.cercat.domain.common.Image;
import com.cos.cercat.domain.certificate.Subject;

import java.util.List;

public record QuestionWithSubjectSeq(
        Integer subjectSeq,
        Image image,
        List<QuestionOption> questionOptions,
        QuestionContent questionContent
) {

    public NewQuestion toNewQuestion(List<Subject> subjectList) {
        Subject matchingSubject = findMatchingSubject(subjectList);
        return new NewQuestion(matchingSubject, image, questionOptions, questionContent);
    }

    public int getScore() {
        return questionContent.score();
    }

    private Subject findMatchingSubject(List<Subject> subjectList) {
        return subjectList.stream()
                .filter(subject -> subject.subjectInfo().subjectSequence().equals(subjectSeq))
                .findFirst()
                .orElseThrow();
    }
}
