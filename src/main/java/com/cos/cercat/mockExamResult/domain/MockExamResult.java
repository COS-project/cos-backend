package com.cos.cercat.mockExamResult.domain;

import com.cos.cercat.mockExam.domain.MockExam;
import com.cos.cercat.user.domain.User;
import com.cos.cercat.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "mockExamResult", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SubjectResult> subjectResults = new ArrayList<>();

    private int round; //유저가 모의고사를 푼 횟수

    private MockExamResult(MockExam mockExam, User user, int round) {
        this.mockExam = mockExam;
        this.user = user;
        this.round = round;
    }

    public static MockExamResult of(MockExam mockExam, User user, int round) {
        return new MockExamResult(
                mockExam,
                user,
                round
        );
    }

    public void addAllSubjectResults(List<SubjectResult> subjectResults) {
        for (SubjectResult subjectResult : subjectResults) {
            subjectResult.setMockExamResult(this);
        }
        this.subjectResults.addAll(subjectResults);
    }
}
