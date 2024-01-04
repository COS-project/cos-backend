package com.cos.cercat.mockExam.domain.entity;

import com.cos.cercat.certificate.domain.entity.Certificate;
import com.cos.cercat.common.entity.BaseTimeEntity;
import com.cos.cercat.mockExam.app.dto.MockExamInfoDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MockExam extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mock_exam_id")
    private  Long id;

    private String name;

    private Integer examYear;

    private Integer round;

    @ManyToOne
    @JoinColumn(name = "certificate_id")
    private Certificate certificate;

    private MockExam(String name, Integer examYear, Integer round, Certificate certificate) { //TODO: 자격증(certificate_id) 참조
        this.name = name;
        this.examYear = examYear;
        this.round = round;
        this.certificate = certificate;
    }

    public static MockExam of(MockExamInfoDto infoDto, Certificate certificate) {
        return new MockExam(infoDto.name(), infoDto.examYear(), infoDto.round(), certificate);
    }
}
