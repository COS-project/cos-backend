package com.cos.cercat.mockExam.domain;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.global.entity.BaseTimeEntity;
import com.cos.cercat.mockExam.dto.MockExamDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MockExam extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mock_exam_id")
    private  Long id;

    private Integer examYear;

    private Integer round;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "certificate_id")
    private Certificate certificate;

    private MockExam(Integer examYear, Integer round, Certificate certificate) {
        this.examYear = examYear;
        this.round = round;
        this.certificate = certificate;
    }

    public static MockExam of(MockExamDTO dto) {
        return new MockExam(dto.examYear(), dto.round(), dto.certificateDTO().toEntity());
    }
}
