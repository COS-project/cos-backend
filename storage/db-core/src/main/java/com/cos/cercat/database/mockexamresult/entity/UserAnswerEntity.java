package com.cos.cercat.database.mockexamresult.entity;

import com.cos.cercat.database.mockexam.entity.QuestionEntity;
import com.cos.cercat.database.user.entity.UserEntity;
import com.cos.cercat.domain.mockexamresult.UserAnswer;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;

import static org.hibernate.annotations.OnDeleteAction.*;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "user_answer",
        indexes = @Index(name = "idx_user_answer_user_id", columnList = "is_reviewed, is_correct")
)
public class UserAnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_answer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_result_id")
    @OnDelete(action =  CASCADE)
    private SubjectResultEntity subjectResultEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @OnDelete(action =  CASCADE)
    private QuestionEntity questionEntity;

    private int selectOptionSeq;

    private long takenTime;

    private boolean isCorrect;

    @Builder.Default
    private boolean isReviewed = false;

    public UserAnswerEntity(UserEntity userEntity,
                            QuestionEntity questionEntity,
                            SubjectResultEntity subjectResultEntity,
                            int selectOptionSeq,
                            long takenTime,
                            boolean isCorrect,
                            boolean isReviewed) {
        this.userEntity = userEntity;
        this.questionEntity = questionEntity;
        this.subjectResultEntity = subjectResultEntity;
        this.selectOptionSeq = selectOptionSeq;
        this.takenTime = takenTime;
        this.isCorrect = isCorrect;
        this.isReviewed = isReviewed;
    }

    public static UserAnswerEntity from(UserAnswer userAnswer) {
        return UserAnswerEntity.builder()
                .id(userAnswer.getId())
                .userEntity(UserEntity.from(userAnswer.getOwner()))
                .questionEntity(QuestionEntity.from(userAnswer.getQuestion()))
                .subjectResultEntity(SubjectResultEntity.from(userAnswer.getSubjectResultId()))
                .selectOptionSeq(userAnswer.getSelectOptionSeq())
                .takenTime(userAnswer.getTakenTime())
                .isCorrect(userAnswer.isCorrect())
                .isReviewed(userAnswer.isReviewed())
                .build();
    }

    public UserAnswer toDomain() {
        return new UserAnswer(
                id,
                userEntity.toDomain(),
                subjectResultEntity.getId(),
                questionEntity.toDomain(),
                selectOptionSeq,
                takenTime,
                isCorrect,
                isReviewed
        );
    }
}
