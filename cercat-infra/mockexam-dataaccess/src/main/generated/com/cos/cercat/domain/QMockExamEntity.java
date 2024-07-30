package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMockExamEntity is a Querydsl query type for MockExamEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMockExamEntity extends EntityPathBase<MockExamEntity> {

    private static final long serialVersionUID = 1898552322L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMockExamEntity mockExamEntity = new QMockExamEntity("mockExamEntity");

    public final com.cos.cercat.entity.QBaseTimeEntity _super = new com.cos.cercat.entity.QBaseTimeEntity(this);

    public final QCertificateEntity certificateEntity;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> examYear = createNumber("examYear", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> round = createNumber("round", Integer.class);

    public final NumberPath<Long> timeLimit = createNumber("timeLimit", Long.class);

    public final NumberPath<Integer> totalScore = createNumber("totalScore", Integer.class);

    public QMockExamEntity(String variable) {
        this(MockExamEntity.class, forVariable(variable), INITS);
    }

    public QMockExamEntity(Path<? extends MockExamEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMockExamEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMockExamEntity(PathMetadata metadata, PathInits inits) {
        this(MockExamEntity.class, metadata, inits);
    }

    public QMockExamEntity(Class<? extends MockExamEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.certificateEntity = inits.isInitialized("certificateEntity") ? new QCertificateEntity(forProperty("certificateEntity")) : null;
    }

}

