package com.cos.cercat.learning.app;

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
        return studyTimeLogRepository.getStudyTimeSum(goal.getId());
    }

    public long getTodayTotalStudyTime(Goal goal) {
        return studyTimeLogRepository.getTodayStudyTime(goal.getId());
    }
}
