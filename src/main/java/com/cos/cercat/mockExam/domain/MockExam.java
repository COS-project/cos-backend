package com.cos.cercat.mockExam.domain;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.global.common.BaseTimeEntity;
import com.cos.cercat.mockExam.dto.MockExamDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = @Index(name = "idx_examYear_round", columnList = "exam_year, round"))
public class MockExam extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mock_exam_id")
    private  Long id;

    private Integer examYear;

    private Integer round;

    private Long timeLimit;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "certificate_id")
    private Certificate certificate;

    private MockExam(Integer examYear, Integer round, Certificate certificate, Long timeLimit) {
        this.examYear = examYear;
        this.round = round;
        this.certificate = certificate;
        this.timeLimit = timeLimit;
    }

    public static MockExam of(MockExamDTO dto, Long timeLimit) {
        return new MockExam(dto.examYear(),
                dto.round(),
                dto.certificateDTO().toEntity(),
                timeLimit);
    }
}
