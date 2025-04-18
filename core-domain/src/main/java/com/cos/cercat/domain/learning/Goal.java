package com.cos.cercat.domain.learning;


import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.user.Ownable;
import com.cos.cercat.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class Goal implements Ownable {

    private final Long id;
    private final Certificate certificate;
    private final User user;
    private GoalPeriod goalPeriod;
    private GoalContent goalContent;
    private List<GoalSchedule> goalSchedules;

    public static Goal create(User user, Certificate certificate, NewGoal newGoal) {
        return Goal.builder()
                .certificate(certificate)
                .user(user)
                .goalPeriod(newGoal.goalPeriod())
                .goalContent(newGoal.goalContent())
                .goalSchedules(newGoal.goalSchedules())
                .build();
    }

    public List<GoalSchedule> getStudySchedules() {
        return this.goalSchedules.stream()
                .filter(studySchedule -> studySchedule.getScheduleType() == ScheduleType.STUDY)
                .toList();
    }

    public List<GoalSchedule> getMockExamSchedules() {
        return this.goalSchedules.stream()
                .filter(studySchedule -> studySchedule.getScheduleType() == ScheduleType.MOCK_EXAM)
                .toList();
    }

    public void updateGoal(NewGoal newGoal) {
        this.goalPeriod = newGoal.goalPeriod();
        this.goalContent = newGoal.goalContent();
        updateSchedules(newGoal.goalSchedules());
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
    public User getOwner() {
        return this.user;
    }

    @Override
    public boolean isOwner(User user) {
        return this.user.getId().equals(user.getId());
    }


    private void updateSchedules(List<GoalSchedule> goalSchedules) {
        this.goalSchedules.clear();
        this.goalSchedules.addAll(goalSchedules);
    }
}
