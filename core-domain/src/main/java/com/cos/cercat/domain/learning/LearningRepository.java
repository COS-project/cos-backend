package com.cos.cercat.domain.learning;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface LearningRepository {

    void saveStudyTimeLog(GoalId goalId,
                          Long studyTime);

    Goal getGoal(GoalId goalId);

    boolean existsGoal(User user, Certificate certificate);

    List<Goal> findAllGoals(User user, Certificate certificate);

    Optional<Goal> getRecentGoal(User user, Certificate certificate);

    long getTodayStudyTime(Long goalId);

    long getTotalStudyTime(Long goalId);

    void save(Goal goal);
}
