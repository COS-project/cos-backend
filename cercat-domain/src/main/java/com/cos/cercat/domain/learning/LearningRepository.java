package com.cos.cercat.domain.learning;

import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.user.TargetUser;

import java.util.List;

public interface LearningRepository {


    void save(TargetUser targetUser,
              TargetCertificate targetCertificate,
              NewGoal newGoal);

    void saveStudyTimeLog(TargetGoal targetGoal,
                          Long studyTime);

    Goal getGoal(TargetGoal targetGoal);

    boolean existsGoal(TargetCertificate targetCertificate, TargetUser targetUser);

    List<Goal> findAllGoals(TargetCertificate targetCertificate, TargetUser targetUser);

    Goal getRecentGoal(TargetCertificate targetCertificate, TargetUser targetUser);

    long getTodayStudyTime(Long goalId);

    long getTotalStudyTime(Long goalId);

    void update(Goal goal);
}
