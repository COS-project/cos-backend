package com.cos.cercat.mockExam.repository;

import com.cos.cercat.user.domain.User;
import com.cos.cercat.mockExam.domain.MockExam;
import com.cos.cercat.mockExam.domain.MockExamResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MockExamResultRepository extends JpaRepository<MockExamResult, Long> {

    List<MockExamResult> findMockExamResultByMockExamAndUser(MockExam mockExam, User user);

    int countMockExamResultsByMockExamAndUser(MockExam mockExam, User user);

}
