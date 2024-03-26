package com.cos.cercat.domain.certificate.repository;

import com.cos.cercat.domain.certificate.domain.Certificate;
import com.cos.cercat.domain.certificate.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findSubjectsByCertificate(Certificate certificate);
}
