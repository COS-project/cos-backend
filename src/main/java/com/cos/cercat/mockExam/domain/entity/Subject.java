package com.cos.cercat.mockExam.domain.entity;

import com.cos.cercat.certificate.domain.entity.Certificate;
import com.cos.cercat.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
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
}
