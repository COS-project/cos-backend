package com.cos.cercat.certificate.domain;

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
    @JoinColumn(name = "certificate_exam_info_id")
    private ExamInfo examInfo;

    private String subjectName;

    private Integer numberOfQuestions;

    private Integer totalScore;

}
