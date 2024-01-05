package com.cos.cercat.mockExam.domain.repository;

import com.cos.cercat.Member.domain.entity.User;
import com.cos.cercat.mockExam.domain.entity.MockExam;
import com.cos.cercat.mockExam.domain.entity.MockExamResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MockExamResultRepository extends JpaRepository<MockExamResult, Long> {

    List<MockExamResult> findMockExamResultByMockExamAndUser(MockExam mockExam, User user);

    int countMockExamResultsByMockExamAndUser(MockExam mockExam, User user);

}
