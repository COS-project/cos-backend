package com.cos.cercat.domain.mockExamResult.service;

import com.cos.cercat.domain.certificate.domain.Certificate;
import com.cos.cercat.domain.mockExamResult.repository.SubjectResultRepository;
import com.cos.cercat.domain.user.domain.User;
import com.cos.cercat.domain.mockExamResult.dto.SubjectResultsAVG;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectResultService {

    private final SubjectResultRepository subjectResultRepository;

    public List<SubjectResultsAVG> getSubjectResultsAVG(Certificate certificate, User user, LocalDateTime goalStartDateTime) {
        return subjectResultRepository.getSubjectResultsAVG(certificate.getId(), user.getId(), goalStartDateTime);
    }

}
