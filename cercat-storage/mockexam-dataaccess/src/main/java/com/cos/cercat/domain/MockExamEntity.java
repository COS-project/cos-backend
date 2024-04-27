package com.cos.cercat.domain;

import com.cos.cercat.dto.MockExamDTO;
import com.cos.cercat.entity.BaseTimeEntity;
import com.cos.cercat.mockexam.MockExam;
import com.cos.cercat.mockexam.MockExamSession;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private int totalScore;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "certificate_id")
    private CertificateEntity certificateEntity;

    public static MockExamEntity of(MockExamDTO dto, long timeLimit, int totalScore) {
        return MockExamEntity.builder()
                .examYear(dto.examYear())
                .round(dto.round())
                .timeLimit(timeLimit)
                .totalScore(totalScore)
                .certificateEntity(dto.certificateDTO().toEntity())
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
                certificateEntity.toDomain()
        );
    }


}
