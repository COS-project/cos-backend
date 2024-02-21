package com.cos.cercat.learning.domain;

import com.cos.cercat.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class StudyTimeLog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Goal goal;

    private Long studyTime;

    private StudyTimeLog(Goal goal, Long studyTime) {
        this.goal = goal;
        this.studyTime = studyTime;
    }

    public static StudyTimeLog of(Goal goal, Long studyTime) {
        return new StudyTimeLog(
                goal,
                studyTime
        );
    }
}
