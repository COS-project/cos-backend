package com.cos.cercat.service;

import com.cos.cercat.domain.Certificate;
import com.cos.cercat.domain.User;
import com.cos.cercat.repository.SubjectResultRepository;
import com.cos.cercat.dto.SubjectResultsAVG;
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
