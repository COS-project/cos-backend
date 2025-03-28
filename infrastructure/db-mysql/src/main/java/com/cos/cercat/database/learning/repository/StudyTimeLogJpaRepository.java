package com.cos.cercat.database.learning.repository;

import com.cos.cercat.database.learning.entity.StudyTimeLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface StudyTimeLogJpaRepository extends JpaRepository<StudyTimeLogEntity, Long> {

    @Query("SELECT SUM(stl.studyTime) FROM StudyTimeLogEntity stl WHERE stl.goalEntity.id = :goalId")
    Long getTotalStudyTime(Long goalId);

    @Query("""
            SELECT SUM(stl.studyTime) FROM StudyTimeLogEntity stl
            WHERE FUNCTION('DATE_FORMAT', stl.createdAt, '%Y-%m-%d') = CURRENT_DATE()
            AND stl.goalEntity.id = :goalId
            """)
    Long getTodayStudyTime(Long goalId);

}
