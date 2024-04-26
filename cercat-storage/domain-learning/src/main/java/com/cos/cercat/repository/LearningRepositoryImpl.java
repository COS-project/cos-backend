package com.cos.cercat.repository;

import com.cos.cercat.common.exception.CustomException;
import com.cos.cercat.common.exception.ErrorCode;
import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.GoalEntity;
import com.cos.cercat.domain.StudyTimeLogEntity;
import com.cos.cercat.domain.UserEntity;
import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.certificate.TargetCertificate;
import com.cos.cercat.domain.learning.Goal;
import com.cos.cercat.domain.learning.LearningRepository;
import com.cos.cercat.domain.learning.NewGoal;
import com.cos.cercat.domain.learning.TargetGoal;
import com.cos.cercat.domain.user.TargetUser;
import com.cos.cercat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class LearningRepositoryImpl implements LearningRepository {

    private final GoalJpaRepository goalJpaRepository;
    private final StudyTimeLogJpaRepository studyTimeJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final CertificateJpaRepository certificateJpaRepository;


    @Override
    public void save(TargetUser targetUser,
                     TargetCertificate targetCertificate,
                     NewGoal newGoal) {
        UserEntity userEntity = userJpaRepository.getReferenceById(targetUser.userId());
        CertificateEntity certificateEntity = certificateJpaRepository.getReferenceById(targetCertificate.certificateId());
        goalJpaRepository.save(GoalEntity.of(certificateEntity, userEntity, newGoal));
    }

    @Override
    public void saveStudyTimeLog(TargetGoal targetGoal,
                                 Long studyTime) {
        GoalEntity goalEntity = goalJpaRepository.getReferenceById(targetGoal.goalId());

        studyTimeJpaRepository.save(StudyTimeLogEntity.of(goalEntity, studyTime));
    }

    @Override
    public Goal getGoal(TargetGoal targetGoal) {
        return goalJpaRepository.findById(targetGoal.goalId()).orElseThrow(
                () -> new CustomException(ErrorCode.GOAL_NOT_FOUND)).toDomain();
    }

    @Override
    public boolean existsGoal(TargetCertificate targetCertificate, TargetUser targetUser) {
        return goalJpaRepository.existsGoalByUserIdAndCertificateId(targetCertificate.certificateId(), targetUser.userId());
    }

    @Override
    public List<Goal> findAllGoals(TargetCertificate targetCertificate, TargetUser targetUser) {
        return goalJpaRepository.findGoalsByUserIdAndCertificateId(targetUser.userId(), targetCertificate.certificateId()).stream()
                .map(GoalEntity::toDomain)
                .toList();
    }

    @Override
    public Goal getRecentGoal(User user, Certificate certificate) {
        return goalJpaRepository.findRecentGoalByUserIdAndCertificateId(user.id(), certificate.id())
                .orElseThrow(() -> new CustomException(ErrorCode.GOAL_NOT_FOUND))
                .toDomain();
    }

    @Override
    public long getTodayStudyTime(Long goalId) {
        return studyTimeJpaRepository.getTodayStudyTime(goalId);
    }

    @Override
    public long getTotalStudyTime(Long goalId) {
        return studyTimeJpaRepository.getTotalStudyTime(goalId);
    }

    @Override
    public void update(Goal goal) {
        goalJpaRepository.save(GoalEntity.from(goal));
    }
}
