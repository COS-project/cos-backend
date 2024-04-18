package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGoal is a Querydsl query type for Goal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGoal extends EntityPathBase<Goal> {

    private static final long serialVersionUID = 1072500873L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGoal goal = new QGoal("goal");

    public final QCertificateEntity certificateEntity;

    public final NumberPath<Integer> goalMockExams = createNumber("goalMockExams", Integer.class);

    public final NumberPath<Integer> goalPrepareDays = createNumber("goalPrepareDays", Integer.class);

    public final NumberPath<Integer> goalScore = createNumber("goalScore", Integer.class);

    public final NumberPath<Long> goalStudyTime = createNumber("goalStudyTime", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> mockExamsPerDay = createNumber("mockExamsPerDay", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> prepareFinishDateTime = createDateTime("prepareFinishDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> prepareStartDateTime = createDateTime("prepareStartDateTime", java.time.LocalDateTime.class);

    public final QRepeatDays repeatDays;

    public final NumberPath<Long> studyTimePerDay = createNumber("studyTimePerDay", Long.class);

    public final QUserEntity userEntity;

    public QGoal(String variable) {
        this(Goal.class, forVariable(variable), INITS);
    }

    public QGoal(Path<? extends Goal> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGoal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGoal(PathMetadata metadata, PathInits inits) {
        this(Goal.class, metadata, inits);
    }

    public QGoal(Class<? extends Goal> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.certificateEntity = inits.isInitialized("certificateEntity") ? new QCertificateEntity(forProperty("certificateEntity")) : null;
        this.repeatDays = inits.isInitialized("repeatDays") ? new QRepeatDays(forProperty("repeatDays")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new QUserEntity(forProperty("userEntity"), inits.get("userEntity")) : null;
    }

}

