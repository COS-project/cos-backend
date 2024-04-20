package com.cos.cercat.domain;

import com.cos.cercat.domain.mockexam.MockExam;
import com.cos.cercat.domain.mockexam.MockExamSession;
import com.cos.cercat.dto.MockExamDTO;
import com.cos.cercat.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    private Long timeLimit;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "certificate_id")
    private CertificateEntity certificateEntity;

    private MockExamEntity(Integer examYear, Integer round, CertificateEntity certificateEntity, Long timeLimit) {
        this.examYear = examYear;
        this.round = round;
        this.certificateEntity = certificateEntity;
        this.timeLimit = timeLimit;
    }

    public static MockExamEntity of(MockExamDTO dto, Long timeLimit) {
        return new MockExamEntity(dto.examYear(),
                dto.round(),
                dto.certificateDTO().toEntity(),
                timeLimit);
    }

    public static MockExamEntity from(MockExam mockExam) {
        return new MockExamEntity(
                mockExam.mockExamSession().examYear(),
                mockExam.mockExamSession().round(),
                CertificateEntity.from(mockExam.certificate()),
                mockExam.timeLimit()
        );
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
