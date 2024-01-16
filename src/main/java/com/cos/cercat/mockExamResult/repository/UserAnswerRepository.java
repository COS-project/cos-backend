package com.cos.cercat.mockExamResult.repository;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.mockExamResult.domain.MockExamResult;
import com.cos.cercat.mockExamResult.domain.UserAnswer;
import com.cos.cercat.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {

    @Query("SELECT ua FROM UserAnswer ua " +
            "JOIN FETCH ua.question q " +
            "JOIN q.mockExam m " +
            "JOIN m.certificate c " +
            "WHERE ua.user = :user AND c = :certificate AND ua.is_correct = false ")
    Slice<UserAnswer> getUserAnswersByUserAndCertificate(Pageable pageable,
                                                         @Param("user") User user,
                                                         @Param("certificate") Certificate certificate);

    @Query("""
            SELECT ua FROM UserAnswer ua
            JOIN FETCH ua.question q
            JOIN ua.subjectResult sr
            WHERE ua.is_correct = false AND sr.mockExamResult = :mockExamResult
            """)
    Slice<UserAnswer> getUserAnswersByMockExamResult(Pageable pageable, @Param("mockExamResult") MockExamResult mockExamResult);

}
