package com.cos.cercat.mockExam.domain.entity;

import com.cos.cercat.certificate.domain.entity.Certificate;
import com.cos.cercat.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Subject extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "certificate_id")
    private Certificate certificate;

    private String subjectName;

    private int numberOfQuestions;

    private int totalScore;

    private Subject(Certificate certificate, String subjectName, int numberOfQuestions, int totalScore) {
        this.certificate = certificate;
        this.subjectName = subjectName;
        this.numberOfQuestions = numberOfQuestions;
        this.totalScore = totalScore;
    }
}
