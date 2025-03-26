package com.cos.cercat.domain.learning;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.user.User;

import java.util.List;

public interface LearningRepository {


    void save(User user,
              Certificate certificate,
              NewGoal newGoal);

    void saveStudyTimeLog(GoalId goalId,
                          Long studyTime);

    Goal getGoal(GoalId goalId);

    boolean existsGoal(User user, Certificate certificate);

    List<Goal> findAllGoals(User user, Certificate certificate);

    Goal getRecentGoal(User user, Certificate certificate);

    long getTodayStudyTime(Long goalId);

    long getTotalStudyTime(Long goalId);

    void update(Goal goal);
}
