package com.cos.cercat.repository;

import com.cos.cercat.domain.CertificateEntity;
import com.cos.cercat.domain.Goal;
import com.cos.cercat.domain.UserEntity;
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
    Optional<Goal> findRecentGoalByUserEntityAndCertificateEntity(UserEntity userEntity, CertificateEntity certificateEntity);

    Boolean existsGoalByUserEntityAndCertificateEntity(UserEntity userEntity, CertificateEntity certificateEntity);

    List<Goal> findGoalsByUserEntityAndCertificateEntity(UserEntity userEntity, CertificateEntity certificateEntity);

}
