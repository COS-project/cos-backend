package com.cos.cercat.learning;

import com.cos.cercat.certificate.Certificate;
import com.cos.cercat.certificate.TargetCertificate;
import com.cos.cercat.user.TargetUser;
import com.cos.cercat.user.User;

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

    Goal getRecentGoal(User user, Certificate certificate);

    long getTodayStudyTime(Long goalId);

    long getTotalStudyTime(Long goalId);

    void update(Goal goal);
}
