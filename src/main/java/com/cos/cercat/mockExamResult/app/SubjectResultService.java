package com.cos.cercat.mockExamResult.app;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.mockExamResult.dto.SubjectResultsAVG;
import com.cos.cercat.mockExamResult.repository.SubjectResultRepository;
import com.cos.cercat.user.domain.User;
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
