package com.cos.cercat.learning.service;

import com.cos.cercat.learning.domain.Goal;
import com.cos.cercat.learning.domain.StudyTimeLog;
import com.cos.cercat.learning.repository.StudyTimeLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudyTimeLogService {

    private final StudyTimeLogRepository studyTimeLogRepository;

    public void createStudyTimeLog(Goal goal, Long studyTime) {
        studyTimeLogRepository.save(StudyTimeLog.of(goal, studyTime));
    }

    public long getTotalStudyTime(Goal goal) {

        Long studyTimeSum = studyTimeLogRepository.getStudyTimeSum(goal.getId());

        if (studyTimeSum == null) {
            return 0;
        }

        return studyTimeSum;
    }

    public long getTodayTotalStudyTime(Goal goal) {

        Long todayStudyTime = studyTimeLogRepository.getTodayStudyTime(goal.getId());

        if (todayStudyTime == null) {
            return 0;
        }

        return todayStudyTime;
    }
}
