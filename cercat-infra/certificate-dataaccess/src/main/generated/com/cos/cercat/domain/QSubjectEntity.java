package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubjectEntity is a Querydsl query type for SubjectEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubjectEntity extends EntityPathBase<SubjectEntity> {

    private static final long serialVersionUID = -719566983L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubjectEntity subjectEntity = new QSubjectEntity("subjectEntity");

    public final com.cos.cercat.entity.QBaseTimeEntity _super = new com.cos.cercat.entity.QBaseTimeEntity(this);

    public final QCertificateEntity certificateEntity;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> numberOfQuestions = createNumber("numberOfQuestions", Integer.class);

    public final StringPath subjectName = createString("subjectName");

    public final NumberPath<Integer> subjectSequence = createNumber("subjectSequence", Integer.class);

    public final NumberPath<Integer> totalScore = createNumber("totalScore", Integer.class);

    public QSubjectEntity(String variable) {
        this(SubjectEntity.class, forVariable(variable), INITS);
    }

    public QSubjectEntity(Path<? extends SubjectEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubjectEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubjectEntity(PathMetadata metadata, PathInits inits) {
        this(SubjectEntity.class, metadata, inits);
    }

    public QSubjectEntity(Class<? extends SubjectEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.certificateEntity = inits.isInitialized("certificateEntity") ? new QCertificateEntity(forProperty("certificateEntity")) : null;
    }

}

