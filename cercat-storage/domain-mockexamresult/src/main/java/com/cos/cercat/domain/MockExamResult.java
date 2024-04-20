package com.cos.cercat.domain;

import com.cos.cercat.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(indexes = @Index(name = "idx_mock_exam_result_user_id", columnList = "created_at, round"))
public class MockExamResult extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mock_exam_result_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mock_exam_id")
    private MockExamEntity mockExamEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Embedded
    private SubjectResults subjectResults = new SubjectResults();

    private Integer totalScore;

    private Integer round; //유저가 모의고사를 푼 횟수

    private MockExamResult(MockExamEntity mockExamEntity, UserEntity userEntity, Integer round, SubjectResults subjectResults, Integer totalScore) {
        this.mockExamEntity = mockExamEntity;
        this.userEntity = userEntity;
        this.round = round;
        setSubjectResults(subjectResults);
        this.totalScore = totalScore;
    }

    public static MockExamResult of(MockExamEntity mockExamEntity, UserEntity userEntity, Integer round, SubjectResults subjectResults, Integer totalScore) {
        return new MockExamResult(
                mockExamEntity,
                userEntity,
                round,
                subjectResults,
                totalScore
        );
    }

    public boolean isAuthorized(UserEntity userEntity) {
        return this.userEntity.equals(userEntity);
    }
    private void setSubjectResults(SubjectResults subjectResults) {
        for (SubjectResult subjectResult : subjectResults.getSubjectResults()) {
            subjectResult.setMockExamResult(this);
        }
        this.subjectResults = subjectResults;
    }
}
