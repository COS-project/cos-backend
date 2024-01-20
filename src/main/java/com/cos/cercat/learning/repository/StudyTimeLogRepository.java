package com.cos.cercat.learning.repository;

import com.cos.cercat.learning.domain.StudyTimeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface StudyTimeLogRepository extends JpaRepository<StudyTimeLog, Long> {

    @Query("SELECT SUM(stl.studyTime) FROM StudyTimeLog stl WHERE stl.goal.id = :goalId")
    long getStudyTimeSum(@Param("goalId") Long goalId);

    @Query("""
            SELECT SUM(stl.studyTime) FROM StudyTimeLog stl
            WHERE FUNCTION('DATE_FORMAT', stl.createdAt, '%Y-%m-%d') = CURRENT_DATE()
            AND stl.goal.id = :goalId
            """)
    int getTodayStudyTime(@Param("goalId") Long goalId);

}
