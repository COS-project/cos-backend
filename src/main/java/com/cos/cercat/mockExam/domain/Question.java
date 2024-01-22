package com.cos.cercat.mockExam.domain;

import com.cos.cercat.global.entity.BaseTimeEntity;
import com.cos.cercat.certificate.domain.Subject;
import com.cos.cercat.global.entity.Image;
import com.cos.cercat.mockExam.domain.embededId.QuestionOptionPK;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.regex.Matcher;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Question extends BaseTimeEntity {

    private static final int MAX_OPTION_SIZE = 5;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Subject subject;

    @OneToOne
    @JoinColumn(name = "image_id")
    @Setter
    private Image questionImage;

    private int questionSeq;

    private String questionText;

    @Embedded
    private QuestionOptions questionOptions = new QuestionOptions();

    private int correct_option;

    private int score;

    private Question(MockExam mockExam) {
        this.mockExam = mockExam;
    }

    public static Question from(MockExam mockExam) {
        return new Question(
                mockExam
        );
    }

    public void setContent(List<String> content) {
        if (content.size() <= MAX_OPTION_SIZE) {
            for (String option : content) {
                setOption(option);
            }
        }

    }

    private void setOption(String option) {
        char number = option.charAt(0);

        switch ((int) number) {
            case 0x2460 -> {
                QuestionOption questionOption = QuestionOption.builder()
                        .questionOptionPK(QuestionOptionPK.from(1))
                        .question(this)
                        .optionContent(option.substring(1))
                        .optionImage(null)
                        .build();
                questionOptions.add(questionOption);
            }
            case 0x2461 -> {
                QuestionOption questionOption = QuestionOption.builder()
                        .questionOptionPK(QuestionOptionPK.from(2))
                        .question(this)
                        .optionContent(option.substring(1))
                        .optionImage(null)
                        .build();
                questionOptions.add(questionOption);
            }
            case 0x2462 -> {
                QuestionOption questionOption = QuestionOption.builder()
                        .questionOptionPK(QuestionOptionPK.from(3))
                        .question(this)
                        .optionContent(option.substring(1))
                        .optionImage(null)
                        .build();
                questionOptions.add(questionOption);
            }
            case 0x2463 -> {
                QuestionOption questionOption = QuestionOption.builder()
                        .questionOptionPK(QuestionOptionPK.from(4))
                        .question(this)
                        .optionContent(option.substring(1))
                        .optionImage(null)
                        .build();
                questionOptions.add(questionOption);
            }
            case 0x0040 -> score = Integer.parseInt(option.substring(1));
        }
    }

    public String getImageUrl() {
        return (this.questionImage != null) ? questionImage.getImageUrl() : "";
    }

    public void setCorrectOption(String answer) {
        char answerChar = answer.charAt(0);
        switch ((int) answerChar) {
            case 0x2460 -> this.correct_option = 1;
            case 0x2461 -> this.correct_option = 2;
            case 0x2462 -> this.correct_option = 3;
            case 0x2463 -> this.correct_option = 4;
        }
    }

    public void setSeqAndTitle(Matcher matcher) {
        this.questionSeq = Integer.parseInt(matcher.group(1));// 번호
        this.questionText = matcher.group(2).trim(); // 내용
    }
}

