package com.cos.cercat.infra.entity;

import com.cos.cercat.domain.learning.ScheduleType;
import com.cos.cercat.domain.learning.StudySchedule;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;

import java.time.DayOfWeek;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

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
    @OnDelete(action = CASCADE)
    @Setter
    private GoalEntity goalEntity;

    @Enumerated(EnumType.STRING)
    private ScheduleType scheduleType;

    @Enumerated(EnumType.STRING)
    private DayOfWeek repeatDayOfWeek;

    public StudyScheduleEntity(ScheduleType scheduleType, DayOfWeek repeatDayOfWeek) {
        this.scheduleType = scheduleType;
        this.repeatDayOfWeek = repeatDayOfWeek;
    }

    public static StudyScheduleEntity from(StudySchedule studySchedule) {
        return new StudyScheduleEntity(
                studySchedule.getScheduleType(),
                studySchedule.getRepeatDayOfWeek()
        );
    }

    public StudySchedule toDomain() {
        return new StudySchedule(
                id,
                scheduleType,
                repeatDayOfWeek
        );
    }
}
