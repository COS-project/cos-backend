package com.cos.cercat.learning.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudyTimeLog is a Querydsl query type for StudyTimeLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudyTimeLog extends EntityPathBase<StudyTimeLog> {

    private static final long serialVersionUID = 533625980L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudyTimeLog studyTimeLog = new QStudyTimeLog("studyTimeLog");

    public final com.cos.cercat.common.domain.QBaseTimeEntity _super = new com.cos.cercat.common.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QGoal goal;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Long> studyTime = createNumber("studyTime", Long.class);

    public QStudyTimeLog(String variable) {
        this(StudyTimeLog.class, forVariable(variable), INITS);
    }

    public QStudyTimeLog(Path<? extends StudyTimeLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudyTimeLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudyTimeLog(PathMetadata metadata, PathInits inits) {
        this(StudyTimeLog.class, metadata, inits);
    }

    public QStudyTimeLog(Class<? extends StudyTimeLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.goal = inits.isInitialized("goal") ? new QGoal(forProperty("goal"), inits.get("goal")) : null;
    }

}

