package com.cos.cercat.mockexamresult;

import com.cos.cercat.mockexam.MockExam;
import com.cos.cercat.user.Ownable;
import com.cos.cercat.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MockExamResult implements Ownable {
    private final Long id;
    private final User user;
    private final MockExam mockExam;
    private final Integer round;
    private final Integer totalScore;
    private final LocalDateTime createdAt;

    public MockExamResult(Long id,
                          User user,
                          MockExam mockExam,
                          Integer round,
                          Integer totalScore,
                          LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.mockExam = mockExam;
        this.round = round;
        this.totalScore = totalScore;
        this.createdAt = createdAt;
    }

    @Override
    public boolean isOwner(User user) {
        return this.user.getId().equals(user.getId());
    }
}