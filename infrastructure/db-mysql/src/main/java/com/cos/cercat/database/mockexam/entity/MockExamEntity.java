package com.cos.cercat.database.mockexam.entity;

import com.cos.cercat.database.certificate.entity.CertificateEntity;
import com.cos.cercat.database.common.entity.BaseTimeEntity;
import com.cos.cercat.domain.mockexam.MockExam;
import com.cos.cercat.domain.mockexam.MockExamSession;
import com.cos.cercat.domain.mockexam.NewMockExam;
import com.cos.cercat.domain.mockexam.MockExamId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "mockExam",
        indexes = @Index(name = "idx_examYear_round", columnList = "exam_year, round")
)
public class MockExamEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mock_exam_id")
    private  Long id;

    private Integer examYear;

    private Integer round;

    private long timeLimit;

    private int maxScore;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "certificate_id")
    @OnDelete(action = CASCADE)
    private CertificateEntity certificateEntity;

    public static MockExamEntity from(NewMockExam newMockExam) {
        return MockExamEntity.builder()
                .examYear(newMockExam.info().session().examYear())
                .round(newMockExam.info().session().round())
                .timeLimit(newMockExam.info().timeLimit())
                .maxScore(newMockExam.info().maxScore())
                .certificateEntity(CertificateEntity.from(newMockExam.certificate()))
                .build();
    }

    public static MockExamEntity from(MockExamId mockExamId) {
        return MockExamEntity.builder()
                .id(mockExamId.mockExamId())
                .build();
    }

    public static MockExamEntity from(MockExam mockExam) {

        if (mockExam == null) {
            return null;
        }

        return MockExamEntity.builder()
                .id(mockExam.id())
                .examYear(mockExam.mockExamSession().examYear())
                .round(mockExam.mockExamSession().round())
                .timeLimit(mockExam.timeLimit())
                .certificateEntity(CertificateEntity.from(mockExam.certificate()))
                .build();
    }

    public MockExam toDomain() {
        return new MockExam(
                id,
                new MockExamSession(examYear, round),
                timeLimit,
                maxScore,
                certificateEntity.toDomain()
        );
    }
}
