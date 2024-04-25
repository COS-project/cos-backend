package com.cos.cercat.repository;

import com.cos.cercat.domain.StudyTimeLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface StudyTimeLogJpaRepository extends JpaRepository<StudyTimeLogEntity, Long> {

    @Query("SELECT SUM(stl.studyTime) FROM StudyTimeLogEntity stl WHERE stl.goalEntity.id = :goalId")
    Long getTotalStudyTime(@Param("goalId") Long goalId);

    @Query("""
            SELECT SUM(stl.studyTime) FROM StudyTimeLogEntity stl
            WHERE FUNCTION('DATE_FORMAT', stl.createdAt, '%Y-%m-%d') = CURRENT_DATE()
            AND stl.goalEntity.id = :goalId
            """)
    Long getTodayStudyTime(@Param("goalId") Long goalId);

}
