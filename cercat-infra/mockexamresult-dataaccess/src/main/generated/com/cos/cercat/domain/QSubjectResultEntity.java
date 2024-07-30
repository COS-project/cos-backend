package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubjectResultEntity is a Querydsl query type for SubjectResultEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubjectResultEntity extends EntityPathBase<SubjectResultEntity> {

    private static final long serialVersionUID = -960616074L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubjectResultEntity subjectResultEntity = new QSubjectResultEntity("subjectResultEntity");

    public final NumberPath<Integer> correctRate = createNumber("correctRate", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMockExamResultEntity mockExamResultEntity;

    public final NumberPath<Integer> numberOfCorrect = createNumber("numberOfCorrect", Integer.class);

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final QSubjectEntity subjectEntity;

    public final NumberPath<Long> totalTakenTime = createNumber("totalTakenTime", Long.class);

    public QSubjectResultEntity(String variable) {
        this(SubjectResultEntity.class, forVariable(variable), INITS);
    }

    public QSubjectResultEntity(Path<? extends SubjectResultEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubjectResultEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubjectResultEntity(PathMetadata metadata, PathInits inits) {
        this(SubjectResultEntity.class, metadata, inits);
    }

    public QSubjectResultEntity(Class<? extends SubjectResultEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.mockExamResultEntity = inits.isInitialized("mockExamResultEntity") ? new QMockExamResultEntity(forProperty("mockExamResultEntity"), inits.get("mockExamResultEntity")) : null;
        this.subjectEntity = inits.isInitialized("subjectEntity") ? new QSubjectEntity(forProperty("subjectEntity"), inits.get("subjectEntity")) : null;
    }

}

