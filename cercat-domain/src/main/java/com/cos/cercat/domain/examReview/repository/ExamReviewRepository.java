package com.cos.cercat.domain.examReview.repository;

import com.cos.cercat.domain.certificate.domain.CertificateExam;
import com.cos.cercat.domain.user.domain.User;
import com.cos.cercat.domain.examReview.domain.ExamReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamReviewRepository extends JpaRepository<ExamReview, Long>, ExamReviewRepositoryCustom {

    Boolean existsExamReviewByUserAndCertificateExam(User user, CertificateExam certificateExam);

}
