package com.cos.cercat.infra.entity;

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
@Table(name = "study_time_log")
public class StudyTimeLogEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private GoalEntity goalEntity;

    private Long studyTime;

    private StudyTimeLogEntity(GoalEntity goalEntity, Long studyTime) {
        this.goalEntity = goalEntity;
        this.studyTime = studyTime;
    }

    public static StudyTimeLogEntity of(GoalEntity goalEntity, Long studyTime) {
        return new StudyTimeLogEntity(
                goalEntity,
                studyTime
        );
    }
}
