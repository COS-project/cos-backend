package com.cos.cercat.certificate.domain;

import com.cos.cercat.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Subject extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_id")
    @Setter
    private Certificate certificate;

    private String subjectName;

    private Integer numberOfQuestions;

    private Integer totalScore;

    public Subject(String subjectName, Integer numberOfQuestions, Integer totalScore) {
        this.subjectName = subjectName;
        this.numberOfQuestions = numberOfQuestions;
        this.totalScore = totalScore;
    }
}
