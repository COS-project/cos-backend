package com.cos.cercat.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = @Index(name = "idx_user_answer_user_id", columnList = "is_reviewed, is_correct"))
public class UserAnswer {

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SubjectResult subjectResult;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity questionEntity;

    private int selectOptionSeq;

    private long takenTime;

    private boolean isCorrect;

    private boolean isReviewed;

    @Builder
    public UserAnswer(UserEntity userEntity, QuestionEntity questionEntity, int selectOptionSeq, long takenTime, boolean isCorrect, boolean isReviewed) {
        this.userEntity = userEntity;
        this.questionEntity = questionEntity;
        this.selectOptionSeq = selectOptionSeq;
        this.takenTime = takenTime;
        this.isCorrect = isCorrect;
        this.isReviewed = isReviewed;
    }

    public void review() {
        this.isReviewed = true;
    }

    public boolean isAuthorized(UserEntity userEntity) {
        return this.userEntity.equals(userEntity);
    }
}
