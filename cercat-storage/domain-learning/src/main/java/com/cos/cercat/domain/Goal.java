package com.cos.cercat.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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
    private CertificateEntity certificateEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

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

    public Goal(CertificateEntity certificateEntity,
                UserEntity userEntity,
                Integer goalScore,
                LocalDateTime prepareStartDateTime,
                LocalDateTime prepareFinishDateTime,
                Integer goalPrepareDays,
                Integer mockExamsPerDay,
                Integer goalMockExams,
                Long studyTimePerDay,
                Long goalStudyTime,
                List<Integer> mockExamRepeatDays,
                List<Integer> studyRepeatDays) {

        this.certificateEntity = certificateEntity;
        this.userEntity = userEntity;
        this.goalScore = goalScore;
        this.prepareStartDateTime = prepareStartDateTime;
        this.prepareFinishDateTime = prepareFinishDateTime;
        this.goalPrepareDays = goalPrepareDays;
        this.mockExamsPerDay = mockExamsPerDay;
        this.goalMockExams = goalMockExams;
        this.studyTimePerDay = studyTimePerDay;
        this.goalStudyTime = goalStudyTime;
        this.getRepeatDays().addAll(convertRepeatDay(mockExamRepeatDays, studyRepeatDays));
    }

    public void updateGoalInfo(Integer goalScore,
                               LocalDateTime prepareStartDateTime,
                               LocalDateTime prepareFinishDateTime,
                               Integer goalPrepareDays,
                               Integer mockExamsPerDay,
                               Integer goalMockExams,
                               Long studyTimePerDay,
                               Long goalStudyTime,
                               List<Integer> mockExamRepeatDays,
                               List<Integer> studyRepeatDays) {
        if (Objects.nonNull(goalScore)) this.goalScore = goalScore;
        if (Objects.nonNull(prepareStartDateTime)) this.prepareStartDateTime = prepareStartDateTime;
        if (Objects.nonNull(prepareFinishDateTime)) this.prepareFinishDateTime = prepareFinishDateTime;
        if (Objects.nonNull(goalPrepareDays)) this.goalPrepareDays = goalPrepareDays;
        if (Objects.nonNull(mockExamsPerDay)) this.mockExamsPerDay = mockExamsPerDay;
        if (Objects.nonNull(goalMockExams)) this.goalMockExams = goalMockExams;
        if (Objects.nonNull(studyTimePerDay)) this.studyTimePerDay = studyTimePerDay;
        if (Objects.nonNull(goalStudyTime)) this.goalStudyTime = goalStudyTime;
        this.getRepeatDays().updateAll(convertRepeatDay(mockExamRepeatDays, studyRepeatDays));
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

    public boolean isAuthorized(UserEntity userEntity) {
        return this.userEntity.equals(userEntity);
    }

    private List<RepeatDay> convertRepeatDay(List<Integer> mockExamRepeatDays, List<Integer> studyRepeatDays) {
        Stream<RepeatDay> mockExamDaysStream = mockExamRepeatDays.stream()
                .map(dayOfWeekValue -> RepeatDay.from(this, RepeatType.MOCK_EXAM, dayOfWeekValue));

        Stream<RepeatDay> studyExamDaysStream = studyRepeatDays.stream()
                .map(dayOfWeekValue -> RepeatDay.from(this, RepeatType.STUDY, dayOfWeekValue));

        return Stream.concat(mockExamDaysStream, studyExamDaysStream).toList();
    }

}
