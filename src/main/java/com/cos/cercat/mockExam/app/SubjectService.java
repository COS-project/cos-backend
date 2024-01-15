package com.cos.cercat.mockExam.app;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.global.exception.CustomException;
import com.cos.cercat.global.exception.ErrorCode;
import com.cos.cercat.mockExam.domain.Subject;
import com.cos.cercat.mockExam.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public Subject getSubject(Long subjectId) {

        return subjectRepository.findById(subjectId).orElseThrow(() ->
                new CustomException(ErrorCode.SUBJECT_NOT_FOUND));
    }

    public List<Subject> getSubjectList(Certificate certificate) {
        return subjectRepository.findSubjectsByCertificate(certificate);
    }

}
