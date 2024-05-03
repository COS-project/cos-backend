package com.cos.cercat.apis.learning.request;


import com.cos.cercat.learning.GoalContent;
import com.cos.cercat.learning.GoalPeriod;
import com.cos.cercat.learning.NewGoal;

import java.time.LocalDateTime;
import java.util.List;

public record GoalRequest(
        Integer goalScore,
        LocalDateTime prepareStartDateTime,
        LocalDateTime prepareFinishDateTime,
        Integer goalPrepareDays,
        Integer mockExamsPerDay,
        Integer goalMockExams,
        List<Integer> mockExamRepeatDays,
        Long studyTimePerDay,
        Long goalStudyTime,
        List<Integer> studyRepeatDays
) {

    public NewGoal toNewGoal() {
        return NewGoal.of(
                new GoalPeriod(
                        prepareStartDateTime,
                        prepareFinishDateTime
                ),
                new GoalContent(
                        goalScore,
                        goalPrepareDays,
                        mockExamsPerDay,
                        goalMockExams,
                        studyTimePerDay,
                        goalStudyTime
                ),
                mockExamRepeatDays,
                studyRepeatDays
        );
    }
}
