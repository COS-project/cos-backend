package com.cos.cercat.learning.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class RepeatDay {

    public static final Integer SUNDAY_VALUE = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "repeat_day_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id")
    @Setter
    private Goal goal;

    @Enumerated(EnumType.STRING)
    private RepeatType repeatType;

    @Enumerated(EnumType.STRING)
    private DayOfWeek repeatDayOfWeek;

    public RepeatDay(Goal goal, RepeatType repeatType, DayOfWeek repeatDayOfWeek) {
        this.goal = goal;
        this.repeatType = repeatType;
        this.repeatDayOfWeek = repeatDayOfWeek;
    }

    public static RepeatDay from(Goal goal, RepeatType repeatType, Integer dayOfWeakValue) {
        return new RepeatDay(
                goal,
                repeatType,
                DayOfWeek.of((dayOfWeakValue > 0) ? dayOfWeakValue : SUNDAY_VALUE)
        );
    }
}
