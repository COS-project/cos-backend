package com.cos.cercat.certificate.domain;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Subject extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "certificate_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Certificate certificate;

    private String subjectName;

    private Integer numberOfQuestions;

    private Integer totalScore;

    private Subject(Certificate certificate, String subjectName, int numberOfQuestions, int totalScore) {
        this.certificate = certificate;
        this.subjectName = subjectName;
        this.numberOfQuestions = numberOfQuestions;
        this.totalScore = totalScore;
    }
}
