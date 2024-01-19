package com.cos.cercat.goal.repository;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.goal.domain.Goal;
import com.cos.cercat.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GoalRepository extends JpaRepository<Goal, Long> {
    Goal findGoalByUserAndCertificate(User user, Certificate certificate);
}
