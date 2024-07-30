package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMockExamResultEntity is a Querydsl query type for MockExamResultEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMockExamResultEntity extends EntityPathBase<MockExamResultEntity> {

    private static final long serialVersionUID = 1068643135L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMockExamResultEntity mockExamResultEntity = new QMockExamResultEntity("mockExamResultEntity");

    public final com.cos.cercat.entity.QBaseTimeEntity _super = new com.cos.cercat.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMockExamEntity mockExamEntity;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> round = createNumber("round", Integer.class);

    public final NumberPath<Integer> totalScore = createNumber("totalScore", Integer.class);

    public final QUserEntity userEntity;

    public QMockExamResultEntity(String variable) {
        this(MockExamResultEntity.class, forVariable(variable), INITS);
    }

    public QMockExamResultEntity(Path<? extends MockExamResultEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMockExamResultEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMockExamResultEntity(PathMetadata metadata, PathInits inits) {
        this(MockExamResultEntity.class, metadata, inits);
    }

    public QMockExamResultEntity(Class<? extends MockExamResultEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.mockExamEntity = inits.isInitialized("mockExamEntity") ? new QMockExamEntity(forProperty("mockExamEntity"), inits.get("mockExamEntity")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new QUserEntity(forProperty("userEntity"), inits.get("userEntity")) : null;
    }

}

