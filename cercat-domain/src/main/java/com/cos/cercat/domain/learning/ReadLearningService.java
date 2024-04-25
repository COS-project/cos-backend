package com.cos.cercat.domain.learning;

import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.mockexamresult.MockExamResultReader;
import com.cos.cercat.domain.user.TargetUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadLearningService {

    private final GoalReader goalReader;
    private final GoalFinder goalFinder;
    private final MockExamResultReader mockExamResultReader;
    private final StudyTimeLogReader studyTimeLogReader;

    public Goal getGoal(TargetGoal targetGoal) {
        return goalReader.read(targetGoal);
    }

    public boolean existsGoal(TargetCertificate targetCertificate, TargetUser targetUser) {
        return goalReader.exists(targetCertificate, targetUser);
    }

    public List<Goal> getAllGoals(TargetCertificate targetCertificate, TargetUser targetUser) {
        return goalFinder.findAll(targetCertificate, targetUser);
    }

    public GoalAchievement getGoalAchievement(TargetCertificate targetCertificate, TargetUser targetUser) {
        Goal goal = goalReader.readRecentGoal(targetCertificate, targetUser);
        int currentMaxScore = mockExamResultReader.readCurrentMaxScore(targetCertificate, targetUser, goal.getGoalPeriod());
        int countTodayMockExamResult = mockExamResultReader.countTodayMockExamResults(targetCertificate, targetUser);
        int countTotalMockExamResults = mockExamResultReader.countTotalMockExamResults(targetCertificate, targetUser, goal.getGoalPeriod());
        long todayTotalStudyTime = studyTimeLogReader.readTodayStudyTime(goal);
        long totalStudyTime = studyTimeLogReader.readTotalStudyTime(goal);

        return GoalAchievement.of(
                goal,
                currentMaxScore,
                countTodayMockExamResult,
                countTotalMockExamResults,
                todayTotalStudyTime,
                totalStudyTime
        );
    }

}
