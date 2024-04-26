package com.cos.cercat.domain.learning;


import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.user.Ownable;
import com.cos.cercat.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
public class Goal implements Ownable {

    private final Long id;
    private final Certificate certificate;
    private final User user;
    private GoalPeriod goalPeriod;
    private GoalContent goalContent;
    private List<StudySchedule> studySchedules;

    public List<StudySchedule> getStudySchedules() {
        return this.studySchedules.stream()
                .filter(studySchedule -> studySchedule.getRepeatType() == RepeatType.STUDY)
                .toList();
    }

    public List<StudySchedule> getMockExamSchedules() {
        return this.studySchedules.stream()
                .filter(studySchedule -> studySchedule.getRepeatType() == RepeatType.MOCK_EXAM)
                .toList();
    }

    public void updateSchedules(List<StudySchedule> studySchedules) {
        this.studySchedules.clear();
        this.studySchedules.addAll(studySchedules);
    }

    public void updateGoal(NewGoal newGoal) {
        this.goalPeriod = newGoal.goalPeriod();
        this.goalContent = newGoal.goalContent();
        updateSchedules(newGoal.studySchedules());
    }

    public Integer getGoalDDay() {
        Date finish = Timestamp.valueOf(goalPeriod.endDateTime());
        Date today = new Date(System.currentTimeMillis());

        Calendar finishCalendar = Calendar.getInstance();
        Calendar todayCalendar = Calendar.getInstance();

        finishCalendar.setTime(finish);
        todayCalendar.setTime(today);

        long diffInMillis = finishCalendar.getTimeInMillis() - todayCalendar.getTimeInMillis();
        long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);

        return (int) diffInDays;
    }

    public int getPrepareMonths() {
        return goalPeriod.getTotalPeriod().getYears() * 12 + goalPeriod.getTotalPeriod().getMonths();
    }

    @Override
    public boolean isOwner(User user) {
        return this.user.id().equals(user.id());
    }
}
