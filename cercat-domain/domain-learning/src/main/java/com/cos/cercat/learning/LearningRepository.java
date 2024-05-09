package com.cos.cercat.learning;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.user.User;

import java.util.List;

public interface LearningRepository {


    void save(User user,
              Certificate certificate,
              NewGoal newGoal);

    void saveStudyTimeLog(TargetGoal targetGoal,
                          Long studyTime);

    Goal getGoal(TargetGoal targetGoal);

    boolean existsGoal(User user, Certificate certificate);

    List<Goal> findAllGoals(User user, Certificate certificate);

    Goal getRecentGoal(User user, Certificate certificate);

    long getTodayStudyTime(Long goalId);

    long getTotalStudyTime(Long goalId);

    void update(Goal goal);
}
