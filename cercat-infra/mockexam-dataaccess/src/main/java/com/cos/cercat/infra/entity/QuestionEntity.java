package com.cos.cercat.infra.entity;

import com.cos.cercat.domain.mockexam.NewQuestion;
import com.cos.cercat.domain.mockexam.Question;
import com.cos.cercat.domain.mockexam.QuestionContent;
import com.cos.cercat.domain.mockexam.TargetMockExam;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @OnDelete(action = CASCADE)
    private MockExamEntity mockExamEntity;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SubjectEntity subjectEntity;

    @OneToOne
    @JoinColumn(name = "image_id")
    @Setter
    private ImageEntity questionImageEntity;

    private int questionSeq;

    @Column(length = 1000)
    private String questionText;

    @OneToMany(mappedBy = "questionEntity", cascade = CascadeType.ALL)
    private List<QuestionOptionEntity> questionOptions = new ArrayList<>();

    private int correctOption;

    private int score;

    public QuestionEntity(Long id,
                          MockExamEntity mockExamEntity,
                          SubjectEntity subjectEntity,
                          ImageEntity questionImageEntity,
                          int questionSeq,
                          String questionText,
                          int correctOption,
                          int score) {
        this.id = id;
        this.mockExamEntity = mockExamEntity;
        this.subjectEntity = subjectEntity;
        this.questionImageEntity = questionImageEntity;
        this.questionSeq = questionSeq;
        this.questionText = questionText;
        this.correctOption = correctOption;
        this.score = score;
    }

    private QuestionEntity(MockExamEntity mockExamEntity) {
        this.mockExamEntity = mockExamEntity;
    }

    public static QuestionEntity from(MockExamEntity mockExamEntity) {
        return new QuestionEntity(
                mockExamEntity
        );
    }

    public static QuestionEntity from(TargetMockExam targetMockExam, NewQuestion newQuestion) {
        QuestionEntity questionEntity = QuestionEntity.builder()
                .mockExamEntity(MockExamEntity.from(targetMockExam))
                .subjectEntity(SubjectEntity.from(newQuestion.subject()))
                .questionSeq(newQuestion.questionContent().questionSequence())
                .questionText(newQuestion.questionContent().questionText())
                .questionOptions(new ArrayList<>())
                .correctOption(newQuestion.questionContent().correctOption())
                .score(newQuestion.questionContent().score())
                .build();

        questionEntity.getQuestionOptions().addAll(
                newQuestion.questionContent().questionOptions().stream()
                        .map(questionOption -> QuestionOptionEntity.from(questionEntity, questionOption))
                        .toList()
        );
        return questionEntity;
    }

    public static QuestionEntity from(Question question) {
        return new QuestionEntity(
                question.id(),
                MockExamEntity.from(question.mockExam()),
                SubjectEntity.from(question.subject()),
                ImageEntity.from(question.questionContent().questionImage()),
                question.questionContent().questionSequence(),
                question.questionContent().questionText(),
                question.questionContent().correctOption(),
                question.questionContent().score()
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
                        questionImageEntity != null ? questionImageEntity.toImage() : null,
                        questionOptions.stream()
                                .map(QuestionOptionEntity::toDomain)
                                .toList(),
                        score
                )
        );
    }

    public String getImageUrl() {
        if (Objects.nonNull(questionImageEntity)) {
            return questionImageEntity.getImageUrl();
        }
        return "";
    }
}

