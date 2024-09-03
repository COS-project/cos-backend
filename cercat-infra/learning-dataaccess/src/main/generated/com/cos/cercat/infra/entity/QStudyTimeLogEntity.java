package com.cos.cercat.infra.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudyTimeLogEntity is a Querydsl query type for StudyTimeLogEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudyTimeLogEntity extends EntityPathBase<StudyTimeLogEntity> {

    private static final long serialVersionUID = -549375350L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudyTimeLogEntity studyTimeLogEntity = new QStudyTimeLogEntity("studyTimeLogEntity");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QGoalEntity goalEntity;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Long> studyTime = createNumber("studyTime", Long.class);

    public QStudyTimeLogEntity(String variable) {
        this(StudyTimeLogEntity.class, forVariable(variable), INITS);
    }

    public QStudyTimeLogEntity(Path<? extends StudyTimeLogEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudyTimeLogEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudyTimeLogEntity(PathMetadata metadata, PathInits inits) {
        this(StudyTimeLogEntity.class, metadata, inits);
    }

    public QStudyTimeLogEntity(Class<? extends StudyTimeLogEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.goalEntity = inits.isInitialized("goalEntity") ? new QGoalEntity(forProperty("goalEntity"), inits.get("goalEntity")) : null;
    }

}

