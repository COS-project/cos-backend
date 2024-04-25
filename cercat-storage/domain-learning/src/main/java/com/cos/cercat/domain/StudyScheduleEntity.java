package com.cos.cercat.domain;

import com.cos.cercat.domain.learning.RepeatType;
import com.cos.cercat.domain.learning.StudySchedule;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "repeat_day")
public class StudyScheduleEntity {

    public static final Integer SUNDAY_VALUE = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "repeat_day_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id")
    @Setter
    private GoalEntity goalEntity;

    @Enumerated(EnumType.STRING)
    private RepeatType repeatType;

    @Enumerated(EnumType.STRING)
    private DayOfWeek repeatDayOfWeek;

    public StudyScheduleEntity(RepeatType repeatType, DayOfWeek repeatDayOfWeek) {
        this.repeatType = repeatType;
        this.repeatDayOfWeek = repeatDayOfWeek;
    }

    public static StudyScheduleEntity from(StudySchedule studySchedule) {
        return new StudyScheduleEntity(
                studySchedule.getRepeatType(),
                studySchedule.getRepeatDayOfWeek()
        );
    }

    public StudySchedule toDomain() {
        return new StudySchedule(
                id,
                repeatType,
                repeatDayOfWeek
        );
    }
}
