package com.cos.cercat.database.learning.entity;

import com.cos.cercat.database.certificate.entity.CertificateEntity;
import com.cos.cercat.database.user.entity.UserEntity;
import com.cos.cercat.domain.learning.Goal;
import com.cos.cercat.domain.learning.GoalContent;
import com.cos.cercat.domain.learning.GoalPeriod;
import com.cos.cercat.domain.learning.NewGoal;
import jakarta.persistence.*;
import java.util.stream.Collectors;
import lombok.*;
import org.hibernate.annotations.OnDelete;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.annotations.OnDeleteAction.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "goal")
public class GoalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_id")
    @OnDelete(action = CASCADE)
    private CertificateEntity certificateEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = CASCADE)
    private UserEntity userEntity;

    @OneToMany(mappedBy = "goalEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GoalScheduleEntity> goalSchedules = new ArrayList<>();

    private Integer goalScore;

    private LocalDateTime prepareStartDateTime;

    private LocalDateTime prepareFinishDateTime;

    private Integer goalPrepareDays;

    private Integer mockExamsPerDay;

    private Integer goalMockExams;

    private Long studyTimePerDay;

    private Long goalStudyTime;

    public GoalEntity(Long goalId,
                      CertificateEntity certificateEntity,
                      UserEntity userEntity,
                      Integer goalScore,
                      LocalDateTime prepareStartDateTime,
                      LocalDateTime prepareFinishDateTime,
                      Integer goalPrepareDays,
                      Integer mockExamsPerDay,
                      Integer goalMockExams,
                      Long studyTimePerDay,
                      Long goalStudyTime,
                      List<GoalScheduleEntity> goalSchedules) {
        this.id = goalId;
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
        addStudySchedule(goalSchedules);
    }

    public GoalEntity(CertificateEntity certificateEntity,
                      UserEntity userEntity,
                      Integer goalScore,
                      LocalDateTime prepareStartDateTime,
                      LocalDateTime prepareFinishDateTime,
                      Integer goalPrepareDays,
                      Integer mockExamsPerDay,
                      Integer goalMockExams,
                      Long studyTimePerDay,
                      Long goalStudyTime,
                      List<GoalScheduleEntity> goalSchedules) {

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
        addStudySchedule(goalSchedules);
    }

    public static GoalEntity of(CertificateEntity certificateEntity,
                                UserEntity userEntity,
                                NewGoal newGoal) {
        GoalContent goalContent = newGoal.goalContent();
        GoalPeriod goalPeriod = newGoal.goalPeriod();
        return new GoalEntity(
                certificateEntity,
                userEntity,
                goalContent.goalScore(),
                goalPeriod.startDateTime(),
                goalPeriod.endDateTime(),
                goalContent.goalPrepareDays(),
                goalContent.mockExamsPerDay(),
                goalContent.goalMockExams(),
                goalContent.studyTimePerDay(),
                goalContent.goalStudyTime(),
                newGoal.goalSchedules().stream()
                        .map(GoalScheduleEntity::from)
                        .toList()
        );
    }

    public static GoalEntity from(Goal goal) {
        GoalContent goalContent = goal.getGoalContent();
        GoalPeriod goalPeriod = goal.getGoalPeriod();
        return new GoalEntity(
                goal.getId(),
                CertificateEntity.from(goal.getCertificate()),
                UserEntity.from(goal.getOwner()),
                goalContent.goalScore(),
                goalPeriod.startDateTime(),
                goalPeriod.endDateTime(),
                goalContent.goalPrepareDays(),
                goalContent.mockExamsPerDay(),
                goalContent.goalMockExams(),
                goalContent.studyTimePerDay(),
                goalContent.goalStudyTime(),
                goal.getGoalSchedules().stream()
                        .map(GoalScheduleEntity::from)
                        .toList()
        );
    }

    public void addStudySchedule(List<GoalScheduleEntity> studyScheduleEntities) {
        studyScheduleEntities.forEach(
                goalScheduleEntity -> goalScheduleEntity.setGoalEntity(this)
        );
        this.getGoalSchedules().addAll(studyScheduleEntities);
    }

    public Goal toDomain() {
        return new Goal(
                id,
                certificateEntity.toDomain(),
                userEntity.toDomain(),
                new GoalPeriod(
                        prepareStartDateTime,
                        prepareFinishDateTime
                ),
                new GoalContent(
                        goalScore,
                        goalPrepareDays,
                        mockExamsPerDay,
                        goalMockExams,
                        studyTimePerDay,
                        goalStudyTime
                ),
                goalSchedules.stream()
                        .map(GoalScheduleEntity::toDomain)
                        .collect(Collectors.toList())
        );
    }
}
