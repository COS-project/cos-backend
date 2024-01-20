package com.cos.cercat.learning.repository;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.learning.domain.Goal;
import com.cos.cercat.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface GoalRepository extends JpaRepository<Goal, Long> {
    Optional<Goal> findGoalByUserAndCertificate(User user, Certificate certificate);

    boolean existsGoalByUserAndCertificate(User user, Certificate certificate);
}
