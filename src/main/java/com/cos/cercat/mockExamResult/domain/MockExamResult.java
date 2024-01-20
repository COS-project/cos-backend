package com.cos.cercat.mockExamResult.domain;

import com.cos.cercat.mockExam.domain.MockExam;
import com.cos.cercat.user.domain.User;
import com.cos.cercat.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MockExamResult extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mock_exam_result_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mock_exam_id")
    private MockExam mockExam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Embedded
    private SubjectResults subjectResults = new SubjectResults();

    private Integer round; //유저가 모의고사를 푼 횟수

    private MockExamResult(MockExam mockExam, User user, Integer round, SubjectResults subjectResults) {
        this.mockExam = mockExam;
        this.user = user;
        this.round = round;
        setSubjectResults(subjectResults);
    }

    public static MockExamResult of(MockExam mockExam, User user, Integer round, SubjectResults subjectResults) {
        return new MockExamResult(
                mockExam,
                user,
                round,
                subjectResults
        );
    }

    private void setSubjectResults(SubjectResults subjectResults) {
        for (SubjectResult subjectResult : subjectResults.getSubjectResults()) {
            subjectResult.setMockExamResult(this);
        }
        this.subjectResults = subjectResults;
    }
}
