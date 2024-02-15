package com.cos.cercat.learning.repository;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.learning.domain.Goal;
import com.cos.cercat.user.domain.User;
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
