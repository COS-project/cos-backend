package com.cos.cercat.domain;

import com.cos.cercat.domain.mockexam.MockExam;
import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.mockexam.QuestionContent;
import com.cos.cercat.entity.Image;
import com.cos.cercat.domain.embededId.QuestionOptionPK;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "question",
        indexes = @Index(name = "idx_question_seq", columnList = "question_seq")
)
public class QuestionEntity {

    private static final int MAX_OPTION_SIZE = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mock_exam_id")
    private MockExamEntity mockExamEntity;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SubjectEntity subjectEntity;

    @OneToOne
    @JoinColumn(name = "image_id")
    @Setter
    private Image questionImage;

    private int questionSeq;

    private String questionText;

    @OneToMany(mappedBy = "questionEntity", cascade = CascadeType.ALL)
    private List<QuestionOptionEntity> questionOptions;

    private int correctOption;

    private int score;

    private QuestionEntity(MockExamEntity mockExamEntity) {
        this.mockExamEntity = mockExamEntity;
    }

    public static QuestionEntity from(MockExamEntity mockExamEntity) {
        return new QuestionEntity(
                mockExamEntity
        );
    }

    public Question toDomain() {
        return new Question(
                id,
                mockExamEntity.toDomain(),
                subjectEntity.toDomain(),
                new QuestionContent(
                        questionSeq,
                        questionText,
                        correctOption,
                        getImageUrl(),
                        questionOptions.stream()
                                .map(QuestionOptionEntity::toDomain)
                                .toList(),
                        score
                )
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
                QuestionOptionEntity questionOptionEntity = QuestionOptionEntity.builder()
                        .questionOptionPK(QuestionOptionPK.from(1))
                        .questionEntity(this)
                        .optionContent(option.substring(1))
                        .optionImage(null)
                        .build();
                questionOptions.add(questionOptionEntity);
            }
            case 0x2461 -> {
                QuestionOptionEntity questionOptionEntity = QuestionOptionEntity.builder()
                        .questionOptionPK(QuestionOptionPK.from(2))
                        .questionEntity(this)
                        .optionContent(option.substring(1))
                        .optionImage(null)
                        .build();
                questionOptions.add(questionOptionEntity);
            }
            case 0x2462 -> {
                QuestionOptionEntity questionOptionEntity = QuestionOptionEntity.builder()
                        .questionOptionPK(QuestionOptionPK.from(3))
                        .questionEntity(this)
                        .optionContent(option.substring(1))
                        .optionImage(null)
                        .build();
                questionOptions.add(questionOptionEntity);
            }
            case 0x2463 -> {
                QuestionOptionEntity questionOptionEntity = QuestionOptionEntity.builder()
                        .questionOptionPK(QuestionOptionPK.from(4))
                        .questionEntity(this)
                        .optionContent(option.substring(1))
                        .optionImage(null)
                        .build();
                questionOptions.add(questionOptionEntity);
            }
            case 0x0040 -> score = Integer.parseInt(option.substring(1));
        }
    }

    public String getImageUrl() {
        if (Objects.nonNull(questionImage)) {
            return questionImage.getImageUrl();
        }
        return "";
    }

    public void setCorrectOption(String answer) {
        char answerChar = answer.charAt(0);
        switch ((int) answerChar) {
            case 0x2460 -> this.correctOption = 1;
            case 0x2461 -> this.correctOption = 2;
            case 0x2462 -> this.correctOption = 3;
            case 0x2463 -> this.correctOption = 4;
        }
    }

    public void setSeqAndTitle(Matcher matcher) {
        this.questionSeq = Integer.parseInt(matcher.group(1));// 번호
        this.questionText = matcher.group(2).trim(); // 내용
    }
}

