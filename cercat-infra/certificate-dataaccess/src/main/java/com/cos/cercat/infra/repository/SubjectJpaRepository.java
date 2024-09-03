package com.cos.cercat.infra.repository;

import com.cos.cercat.infra.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectJpaRepository extends JpaRepository<SubjectEntity, Long> {

    @Query("""
            select s
            from SubjectEntity s
            where s.certificateEntity.id = :certificateId
            """)
    List<SubjectEntity> findSubjectsByCertificateId(Long certificateId);
}
