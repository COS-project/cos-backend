package com.cos.cercat.examReview.repository;

import com.cos.cercat.certificate.domain.CertificateExam;
import com.cos.cercat.examReview.domain.ExamReview;
import com.cos.cercat.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamReviewRepository extends JpaRepository<ExamReview, Long>, ExamReviewRepositoryCustom {

    Boolean existsExamReviewByUserAndCertificateExam(User user, CertificateExam certificateExam);

}
