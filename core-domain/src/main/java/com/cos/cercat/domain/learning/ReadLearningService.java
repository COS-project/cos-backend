package com.cos.cercat.domain.learning;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.CertificateReader;
import com.cos.cercat.domain.certificate.CertificateId;
import com.cos.cercat.domain.user.UserId;
import com.cos.cercat.domain.user.User;
import com.cos.cercat.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadLearningService {

    private final GoalReader goalReader;
    private final GoalFinder goalFinder;
    private final CertificateReader certificateReader;
    private final UserReader userReader;
    private final MockExamResultProvider mockExamResultReader;
    private final StudyTimeLogReader studyTimeLogReader;

    public Goal getGoal(GoalId goalId) {
        return goalReader.read(goalId);
    }

    public boolean existsGoal(UserId userId, CertificateId certificateId) {
        User user = userReader.read(userId);
        Certificate certificate = certificateReader.read(certificateId);
        return goalReader.exists(user, certificate);
    }

    public List<Goal> getAllGoals(UserId userId, CertificateId certificateId) {
        User user = userReader.read(userId);
        Certificate certificate = certificateReader.read(certificateId);
        return goalFinder.findAll(user, certificate);
    }

    public GoalAchievement getGoalAchievement(UserId userId, CertificateId certificateId) {
        User user = userReader.read(userId);
        Certificate certificate = certificateReader.read(certificateId);
        Goal goal = goalReader.readRecentGoal(user, certificate);
        int currentMaxScore = mockExamResultReader.readCurrentMaxScore(user, certificate, goal.getGoalPeriod());
        int countTodayMockExamResult = mockExamResultReader.countTodayMockExamResults(user, certificate);
        int countTotalMockExamResults = mockExamResultReader.countTotalMockExamResults(user, certificate, goal.getGoalPeriod());
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
