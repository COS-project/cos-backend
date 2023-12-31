package com.cos.cercat.mockExam.app.service;

import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.mockExam.domain.entity.Subject;
import com.cos.cercat.mockExam.domain.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public Subject getSubjectById(Long subjectId) {

        return subjectRepository.findById(subjectId).orElseThrow(() ->
                new CustomException(ErrorCode.SUBJECT_NOT_FOUND));

    }

}
