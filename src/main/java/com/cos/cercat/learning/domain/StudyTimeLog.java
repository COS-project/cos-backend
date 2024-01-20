package com.cos.cercat.learning.domain;

import com.cos.cercat.global.entity.BaseTimeEntity;
import com.cos.cercat.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
