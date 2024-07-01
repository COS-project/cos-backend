package com.cos.cercat.certificate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SubjectFinder {

    private final CertificateRepository certificateRepository;

    public List<Subject> find(Certificate certificate) {
        List<Subject> subjects = certificateRepository.findSubject(certificate);
        subjects.sort(Comparator.comparing(subject -> subject.subjectInfo().subjectSequence()));

        return subjects;
    }

}
