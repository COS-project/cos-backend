package com.cos.cercat.learning;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.certificate.CertificateReader;
import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;
import com.cos.cercat.user.UserReader;
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

    public Goal getGoal(TargetGoal targetGoal) {
        return goalReader.read(targetGoal);
    }

    public boolean existsGoal(TargetUser targetUser, TargetCertificate targetCertificate) {
        User user = userReader.read(targetUser);
        Certificate certificate = certificateReader.read(targetCertificate);
        return goalReader.exists(user, certificate);
    }

    public List<Goal> getAllGoals(TargetUser targetUser, TargetCertificate targetCertificate) {
        User user = userReader.read(targetUser);
        Certificate certificate = certificateReader.read(targetCertificate);
        return goalFinder.findAll(user, certificate);
    }

    public GoalAchievement getGoalAchievement(TargetUser targetUser, TargetCertificate targetCertificate) {
        User user = userReader.read(targetUser);
        Certificate certificate = certificateReader.read(targetCertificate);
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
