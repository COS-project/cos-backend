package com.cos.cercat.domain.learning.repository;

import com.cos.cercat.domain.certificate.domain.Certificate;
import com.cos.cercat.domain.user.domain.User;
import com.cos.cercat.domain.learning.domain.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface GoalRepository extends JpaRepository<Goal, Long> {

    @Query("""
            SELECT g FROM Goal g
            ORDER BY g.prepareStartDateTime DESC
            LIMIT 1
            """)
    Optional<Goal> findRecentGoalByUserAndCertificate(User user, Certificate certificate);

    Boolean existsGoalByUserAndCertificate(User user, Certificate certificate);

    List<Goal> findGoalsByUserAndCertificate(User user, Certificate certificate);

}
