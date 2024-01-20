package com.cos.cercat.certificate.app;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.domain.ExamInfo;
import com.cos.cercat.certificate.repository.ExamInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamInfoService {

    private final ExamInfoRepository examInfoRepository;

    public ExamInfo getRecentExamInfo(Certificate certificate) {
        return examInfoRepository.findRecentExamInfo(certificate.getId());
    }

    public void createExamInfo(ExamInfo examInfo) {
        examInfoRepository.save(examInfo);
    }

}
