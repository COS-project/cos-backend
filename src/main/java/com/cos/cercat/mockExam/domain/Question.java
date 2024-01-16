package com.cos.cercat.mockExam.domain;

import com.cos.cercat.global.entity.BaseTimeEntity;
import com.cos.cercat.certificate.domain.Subject;
import jakarta.persistence.*;
import lombok.*;

import java.util.regex.Matcher;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mock_exam_id")
    private MockExam mockExam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    @Setter
    private Subject subject;

    private int questionSeq;

    private String question_text;

    private String option_1;

    private String option_2;

    private String option_3;

    private String option_4;

    private int correct_option;

    private int score;

    private Question(int questionSeq, String question_text, String option_1, String option_2, String option_3, String option_4, int correct_option, int score) {
        this.questionSeq = questionSeq;
        this.question_text = question_text;
        this.option_1 = option_1;
        this.option_2 = option_2;
        this.option_3 = option_3;
        this.option_4 = option_4;
        this.correct_option = correct_option;
        this.score = score;
    }

    private Question(MockExam mockExam) {
        this.mockExam = mockExam;
    }

    public Question of(int questionSeq, String question_text, String option_1, String option_2, String option_3, String option_4, int correct_option, int score) {
        return new Question(
                questionSeq,
                question_text,
                option_1,
                option_2,
                option_3,
                option_4,
                correct_option,
                score
        );
    }

    public static Question from(MockExam mockExam) {
        return new Question(
                mockExam
        );
    }

    public void setOption(String option) {
        char number = option.charAt(0);
        switch ( (int) number) {
            case 0x2460 -> option_1 = option.substring(1);
            case 0x2461 -> option_2 = option.substring(1);
            case 0x2462 -> option_3 = option.substring(1);
            case 0x2463 -> option_4 = option.substring(1);
            case 0x0040 -> score = Integer.parseInt(option.substring(1));
            default -> {}
        }
    }



    public void setCorrectOption(String answer) {
        char answerChar = answer.charAt(0);
        switch ((int) answerChar) {
            case 0x2460 -> this.correct_option = 1;
            case 0x2461 -> this.correct_option = 2;
            case 0x2462 -> this.correct_option = 3;
            case 0x2463 -> this.correct_option = 4;
            default -> {}
        }
    }

    public void setSeqAndTitle(Matcher matcher) {
        this.questionSeq = Integer.parseInt(matcher.group(1));// 번호
        this.question_text = matcher.group(2).trim(); // 내용
    }
}

