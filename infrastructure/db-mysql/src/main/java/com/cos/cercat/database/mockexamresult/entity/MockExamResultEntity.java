package com.cos.cercat.database.mockexamresult.entity;

import com.cos.cercat.database.common.entity.BaseTimeEntity;
import com.cos.cercat.database.mockexam.entity.MockExamEntity;
import com.cos.cercat.database.user.entity.UserEntity;
import com.cos.cercat.domain.mockexamresult.MockExamResult;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Getter
@NoArgsConstructor
@Table(
        name = "mock_exam_result",
        indexes = {
                @Index(name = "idx_mock_exam_result_user_id", columnList = "created_at, round"),
                @Index(name = "idx_mock_exam_stats", columnList = "user_id, mock_exam_id, created_at, total_score")
        }
)
public class MockExamResultEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mock_exam_result_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mock_exam_id")
    @OnDelete(action = CASCADE)
    private MockExamEntity mockExamEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    private Integer totalScore;

    private Integer round; //유저가 모의고사를 푼 횟수

    @Builder
    public MockExamResultEntity(MockExamEntity mockExamEntity, UserEntity userEntity, Integer round, Integer totalScore) {
        this.mockExamEntity = mockExamEntity;
        this.userEntity = userEntity;
        this.round = round;
        this.totalScore = totalScore;
    }

    public MockExamResult toDomain() {
        return new MockExamResult(
                id,
                userEntity.toDomain(),
                mockExamEntity.toDomain(),
                round,
                totalScore,
                createdAt
        );
    }

}
