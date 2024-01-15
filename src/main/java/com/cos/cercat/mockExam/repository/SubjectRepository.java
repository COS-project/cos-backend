package com.cos.cercat.mockExam.repository;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.mockExam.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findSubjectsByCertificate(Certificate certificate);
}
