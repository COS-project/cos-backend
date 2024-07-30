package com.cos.cercat.domain.learning;

import com.cos.cercat.domain.certificate.Certificate;
import com.cos.cercat.domain.user.User;
import java.time.LocalDateTime;
import java.util.List;

public class GoalFixture {

  public static GoalPeriod createGoalPeriod() {
    return new GoalPeriod(
        LocalDateTime.of(2024, 1, 1, 0, 0),
        LocalDateTime.of(2024, 12, 31, 23, 59)
    );
  }

  public static Goal createGoal(Certificate certificate, User user, GoalPeriod goalPeriod) {
    return new Goal(
        1L,
        certificate,
        user,
        goalPeriod,
        new GoalContent(100, 100, 2, 50, 30L, 500L),
        List.of(StudySchedule.from(ScheduleType.STUDY, 3))
    );
  }

  ;

}
