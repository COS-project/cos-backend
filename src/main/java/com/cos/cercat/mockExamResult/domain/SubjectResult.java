package com.cos.cercat.mockExamResult.domain;

import com.cos.cercat.certificate.domain.Subject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_result_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mock_exam_result_id")
    @Setter
    private MockExamResult mockExamResult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @OneToMany(mappedBy = "subjectResult", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserAnswer> userAnswers = new ArrayList<>();

    private int score;

    public SubjectResult(Subject subject, int score) {
        this.subject = subject;
        this.score = score;
    }

    public SubjectResult addAllUserAnswers(List<UserAnswer> userAnswers) {
        for (UserAnswer userAnswer : userAnswers) {
            userAnswer.setSubjectResult(this);
        }
        this.userAnswers.addAll(userAnswers);
        return this;
    }
}
