package com.cos.cercat.certificate.app;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.domain.ExamInfo;
import com.cos.cercat.certificate.repository.ExamInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExamInfoService {

    private final ExamInfoRepository examInfoRepository;

    public ExamInfo getRecentExamInfo(Certificate certificate) {
        return examInfoRepository.findRecentExamInfo(certificate.getId());
    }

    public Boolean isExamDatePassed(Certificate certificate) {
        ExamInfo recentExamInfo = examInfoRepository.findRecentExamInfo(certificate.getId());

        LocalDateTime today = LocalDateTime.now();
        log.info("localTime - {}", today);

        LocalDateTime resultAnnouncementDateTime = recentExamInfo.getExamSchedule().getResultAnnouncementDateTime();

        return today.isAfter(resultAnnouncementDateTime);
    }

    public void createExamInfo(ExamInfo examInfo) {
        examInfoRepository.save(examInfo);
    }

}
