package com.cos.cercat.mockExam.domain.entity;

import com.cos.cercat.Member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

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
    @Setter
    private SubjectResult subjectResult;

    @OneToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String selectOption;

    private long takenTime;

    private boolean is_correct;

    private UserAnswer(Member member, Question question, String selectOption, long takenTime, boolean is_correct) {
        this.member = member;
        this.question = question;
        this.selectOption = selectOption;
        this.takenTime = takenTime;
        this.is_correct = is_correct;
    }

    public static UserAnswer of(Member member, Question question, String selectOption, long takenTime, boolean is_correct) {
        return new UserAnswer(
                member,
                question,
                selectOption,
                takenTime,
                is_correct
        );
    }
}
