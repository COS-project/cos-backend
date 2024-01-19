package com.cos.cercat.goal.domain;

import com.cos.cercat.certificate.domain.Certificate;
import com.cos.cercat.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_id")
    private Certificate certificate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Embedded
    private RepeatDays repeatDays = new RepeatDays();

    private Integer goalScore;

    private LocalDateTime prepareStartDateTime;

    private LocalDateTime prepareFinishDateTime;

    private Integer totalPrepareDays;

    private Integer mockExamsPerDay;

    private Integer totalMockExams;

    private Long studyTimePerDay;

    private Long totalStudyTime;

    @Builder
    public Goal(Certificate certificate,
                User user, Integer goalScore,
                LocalDateTime prepareStartDateTime,
                LocalDateTime prepareFinishDateTime,
                Integer totalPrepareDays,
                Integer mockExamsPerDay,
                Integer totalMockExams,
                Long studyTimePerDay,
                Long totalStudyTime,
                List<RepeatDay> repeatDays) {

        this.certificate = certificate;
        this.user = user;
        this.goalScore = goalScore;
        this.prepareStartDateTime = prepareStartDateTime;
        this.prepareFinishDateTime = prepareFinishDateTime;
        this.totalPrepareDays = totalPrepareDays;
        this.mockExamsPerDay = mockExamsPerDay;
        this.totalMockExams = totalMockExams;
        this.studyTimePerDay = studyTimePerDay;
        this.totalStudyTime = totalStudyTime;
        addAllRepeatDays(repeatDays);
    }

    public Integer getGoalDDay() {
        Date finish = Timestamp.valueOf(this.prepareFinishDateTime);
        Date today = new Date(System.currentTimeMillis());

        Calendar finishCalendar = Calendar.getInstance();
        Calendar todayCalendar = Calendar.getInstance();

        finishCalendar.setTime(finish);
        todayCalendar.setTime(today);

        long diffInMillis = finishCalendar.getTimeInMillis() - todayCalendar.getTimeInMillis();
        long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);

        return (int) diffInDays;
    }

    private void addAllRepeatDays(List<RepeatDay> repeatDays) {
        for (RepeatDay repeatDay : repeatDays) {
            repeatDay.setGoal(this);
        }
        this.repeatDays.addAll(repeatDays);
    }

}
