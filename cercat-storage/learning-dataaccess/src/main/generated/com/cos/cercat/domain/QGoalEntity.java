package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGoalEntity is a Querydsl query type for GoalEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGoalEntity extends EntityPathBase<GoalEntity> {

    private static final long serialVersionUID = 152671692L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGoalEntity goalEntity = new QGoalEntity("goalEntity");

    public final QCertificateEntity certificateEntity;

    public final NumberPath<Integer> goalMockExams = createNumber("goalMockExams", Integer.class);

    public final NumberPath<Integer> goalPrepareDays = createNumber("goalPrepareDays", Integer.class);

    public final NumberPath<Integer> goalScore = createNumber("goalScore", Integer.class);

    public final NumberPath<Long> goalStudyTime = createNumber("goalStudyTime", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> mockExamsPerDay = createNumber("mockExamsPerDay", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> prepareFinishDateTime = createDateTime("prepareFinishDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> prepareStartDateTime = createDateTime("prepareStartDateTime", java.time.LocalDateTime.class);

    public final ListPath<StudyScheduleEntity, QStudyScheduleEntity> studySchedules = this.<StudyScheduleEntity, QStudyScheduleEntity>createList("studySchedules", StudyScheduleEntity.class, QStudyScheduleEntity.class, PathInits.DIRECT2);

    public final NumberPath<Long> studyTimePerDay = createNumber("studyTimePerDay", Long.class);

    public final QUserEntity userEntity;

    public QGoalEntity(String variable) {
        this(GoalEntity.class, forVariable(variable), INITS);
    }

    public QGoalEntity(Path<? extends GoalEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGoalEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGoalEntity(PathMetadata metadata, PathInits inits) {
        this(GoalEntity.class, metadata, inits);
    }

    public QGoalEntity(Class<? extends GoalEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.certificateEntity = inits.isInitialized("certificateEntity") ? new QCertificateEntity(forProperty("certificateEntity")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new QUserEntity(forProperty("userEntity"), inits.get("userEntity")) : null;
    }

}

