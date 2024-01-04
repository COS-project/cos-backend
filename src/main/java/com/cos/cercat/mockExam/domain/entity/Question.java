package com.cos.cercat.mockExam.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.regex.Matcher;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mock_exam_id")
    private MockExam mockExam;

    private int question_seq;

    private String question_text;

    private String option_a;

    private String option_b;

    private String option_c;

    private String option_d;

    private int correct_option;

    private int score;

    private Question(int question_seq, String question_text, String option_a, String option_b, String option_c, String option_d, int correct_option, int score) {
        this.question_seq = question_seq;
        this.question_text = question_text;
        this.option_a = option_a;
        this.option_b = option_b;
        this.option_c = option_c;
        this.option_d = option_d;
        this.correct_option = correct_option;
        this.score = score;
    }

    private Question(MockExam mockExam) {
        this.mockExam = mockExam;
    }

    public Question of(int question_seq, String question_text, String option_a, String option_b, String option_c, String option_d, int correct_option, int score) {
        return new Question(
                question_seq,
                question_text,
                option_a,
                option_b,
                option_c,
                option_d,
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
            case 0x2460 -> option_a = option.substring(1);
            case 0x2461 -> option_b = option.substring(1);
            case 0x2462 -> option_c = option.substring(1);
            case 0x2463 -> option_d = option.substring(1);
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
        this.question_seq = Integer.parseInt(matcher.group(1));// 번호
        this.question_text = matcher.group(2).trim(); // 내용
    }
}

