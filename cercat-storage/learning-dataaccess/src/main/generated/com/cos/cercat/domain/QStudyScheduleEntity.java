package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudyScheduleEntity is a Querydsl query type for StudyScheduleEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudyScheduleEntity extends EntityPathBase<StudyScheduleEntity> {

    private static final long serialVersionUID = 90828621L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudyScheduleEntity studyScheduleEntity = new QStudyScheduleEntity("studyScheduleEntity");

    public final QGoalEntity goalEntity;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<java.time.DayOfWeek> repeatDayOfWeek = createEnum("repeatDayOfWeek", java.time.DayOfWeek.class);

    public final EnumPath<com.cos.cercat.domain.learning.ScheduleType> scheduleType = createEnum("scheduleType", com.cos.cercat.domain.learning.ScheduleType.class);

    public QStudyScheduleEntity(String variable) {
        this(StudyScheduleEntity.class, forVariable(variable), INITS);
    }

    public QStudyScheduleEntity(Path<? extends StudyScheduleEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudyScheduleEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudyScheduleEntity(PathMetadata metadata, PathInits inits) {
        this(StudyScheduleEntity.class, metadata, inits);
    }

    public QStudyScheduleEntity(Class<? extends StudyScheduleEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.goalEntity = inits.isInitialized("goalEntity") ? new QGoalEntity(forProperty("goalEntity"), inits.get("goalEntity")) : null;
    }

}

