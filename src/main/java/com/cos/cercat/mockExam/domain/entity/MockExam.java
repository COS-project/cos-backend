package com.cos.cercat.mockExam.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class MockExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mock_exam_id")
    private  Long id;

    private String description;

    @ManyToOne
    @JoinColumn(name = "certificate_id")
    private Certificate certificate;

    private MockExam(String description, Certificate certificate) { //TODO: 자격증(certificate_id) 참조
        this.description = description;
        this.certificate = certificate;
    }

    public static MockExam of(String description, Certificate certificate) {
        return new MockExam(description, certificate);
    }
}
