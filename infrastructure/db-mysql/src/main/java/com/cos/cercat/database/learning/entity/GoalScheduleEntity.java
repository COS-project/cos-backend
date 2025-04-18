package com.cos.cercat.database.learning.entity;

import com.cos.cercat.domain.learning.GoalSchedule;
import com.cos.cercat.domain.learning.ScheduleType;
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
public class GoalScheduleEntity {

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

    public GoalScheduleEntity(ScheduleType scheduleType, DayOfWeek repeatDayOfWeek) {
        this.scheduleType = scheduleType;
        this.repeatDayOfWeek = repeatDayOfWeek;
    }

    public static GoalScheduleEntity from(GoalSchedule goalSchedule) {
        return new GoalScheduleEntity(
                goalSchedule.getScheduleType(),
                goalSchedule.getRepeatDayOfWeek()
        );
    }

    public GoalSchedule toDomain() {
        return new GoalSchedule(
                id,
                scheduleType,
                repeatDayOfWeek
        );
    }
}
