package com.cos.cercat.database.learning.repository;

import com.cos.cercat.database.certificate.entity.CertificateEntity;
import com.cos.cercat.database.certificate.repository.CertificateJpaRepository;
import com.cos.cercat.database.user.entity.UserEntity;
import com.cos.cercat.database.user.repository.UserJpaRepository;
import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.database.learning.entity.GoalEntity;
import com.cos.cercat.database.learning.entity.StudyTimeLogEntity;
import com.cos.cercat.domain.learning.exception.GoalNotFoundException;
import com.cos.cercat.domain.learning.Goal;
import com.cos.cercat.domain.learning.LearningRepository;
import com.cos.cercat.domain.learning.NewGoal;
import com.cos.cercat.domain.learning.GoalId;
import com.cos.cercat.domain.user.User;
import java.util.Optional;
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
    public void save(User user,
                     Certificate certificate,
                     NewGoal newGoal) {
        UserEntity userEntity = userJpaRepository.getReferenceById(user.getId());
        CertificateEntity certificateEntity = certificateJpaRepository.getReferenceById(certificate.id().value());
        goalJpaRepository.save(GoalEntity.of(certificateEntity, userEntity, newGoal));
    }

    @Override
    public void saveStudyTimeLog(GoalId goalId,
                                 Long studyTime) {
        GoalEntity goalEntity = goalJpaRepository.getReferenceById(goalId.goalId());

        studyTimeJpaRepository.save(StudyTimeLogEntity.of(goalEntity, studyTime));
    }

    @Override
    public Goal getGoal(GoalId goalId) {
        return goalJpaRepository.findById(goalId.goalId()).orElseThrow(
                () -> GoalNotFoundException.EXCEPTION).toDomain();
    }

    @Override
    public boolean existsGoal(User user, Certificate certificate) {
        return goalJpaRepository.existsGoalByUserIdAndCertificateId(user.getId(), certificate.id().value());
    }

    @Override
    public List<Goal> findAllGoals(User user, Certificate certificate) {
        return goalJpaRepository.findGoalsByUserIdAndCertificateId(user.getId(), certificate.id().value()).stream()
                .map(GoalEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Goal> getRecentGoal(User user, Certificate certificate) {
        return goalJpaRepository.findRecentGoalByUserIdAndCertificateId(user.getId(), certificate.id().value())
                .map(GoalEntity::toDomain);
    }

    @Override
    public long getTodayStudyTime(Long goalId) {
        Long todayStudyTime = studyTimeJpaRepository.getTodayStudyTime(goalId);
        return todayStudyTime == null ? 0 : todayStudyTime;
    }

    @Override
    public long getTotalStudyTime(Long goalId) {
        Long totalStudyTime = studyTimeJpaRepository.getTotalStudyTime(goalId);
        return totalStudyTime == null ? 0 : totalStudyTime;
    }

    @Override
    public void update(Goal goal) {
        goalJpaRepository.save(GoalEntity.from(goal));
    }
}
