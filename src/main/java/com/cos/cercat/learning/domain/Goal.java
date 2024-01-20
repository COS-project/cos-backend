package com.cos.cercat.learning.domain;

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

    private Integer goalPrepareDays;

    private Integer mockExamsPerDay;

    private Integer goalMockExams;

    private Long studyTimePerDay;

    private Long goalStudyTime;

    @Builder
    public Goal(Certificate certificate,
                User user, Integer goalScore,
                LocalDateTime prepareStartDateTime,
                LocalDateTime prepareFinishDateTime,
                Integer goalPrepareDays,
                Integer mockExamsPerDay,
                Integer goalMockExams,
                Long studyTimePerDay,
                Long goalStudyTime,
                List<RepeatDay> repeatDays) {

        this.certificate = certificate;
        this.user = user;
        this.goalScore = goalScore;
        this.prepareStartDateTime = prepareStartDateTime;
        this.prepareFinishDateTime = prepareFinishDateTime;
        this.goalPrepareDays = goalPrepareDays;
        this.mockExamsPerDay = mockExamsPerDay;
        this.goalMockExams = goalMockExams;
        this.studyTimePerDay = studyTimePerDay;
        this.goalStudyTime = goalStudyTime;
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
