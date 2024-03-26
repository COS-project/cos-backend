package com.cos.cercat.service;

import com.cos.cercat.domain.Certificate;
import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.Subject;
import com.cos.cercat.repository.SubjectRepository;
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
