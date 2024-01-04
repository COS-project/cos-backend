package com.cos.cercat.mockExam.domain.entity;

import com.cos.cercat.Member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_answer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_result_id")
    private SubjectResult subjectResult;

    @OneToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String selectOption;

    private long takenTime;

    private boolean is_correct;
}
