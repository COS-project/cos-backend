package com.cos.cercat.certificate.repository;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.certificate.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findSubjectsByExamInfo_Certificate(Certificate certificate);
}
