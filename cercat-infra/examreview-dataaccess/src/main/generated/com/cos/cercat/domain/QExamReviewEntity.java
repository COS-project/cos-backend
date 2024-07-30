package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExamReviewEntity is a Querydsl query type for ExamReviewEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExamReviewEntity extends EntityPathBase<ExamReviewEntity> {

    private static final long serialVersionUID = -1695167600L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExamReviewEntity examReviewEntity = new QExamReviewEntity("examReviewEntity");

    public final com.cos.cercat.entity.QBaseTimeEntity _super = new com.cos.cercat.entity.QBaseTimeEntity(this);

    public final QCertificateExamEntity certificateExamEntity;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<com.cos.cercat.domain.examreview.ExamDifficulty> examDifficulty = createEnum("examDifficulty", com.cos.cercat.domain.examreview.ExamDifficulty.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> prepareMonths = createNumber("prepareMonths", Integer.class);

    public final QUserEntity userEntity;

    public QExamReviewEntity(String variable) {
        this(ExamReviewEntity.class, forVariable(variable), INITS);
    }

    public QExamReviewEntity(Path<? extends ExamReviewEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExamReviewEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExamReviewEntity(PathMetadata metadata, PathInits inits) {
        this(ExamReviewEntity.class, metadata, inits);
    }

    public QExamReviewEntity(Class<? extends ExamReviewEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.certificateExamEntity = inits.isInitialized("certificateExamEntity") ? new QCertificateExamEntity(forProperty("certificateExamEntity"), inits.get("certificateExamEntity")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new QUserEntity(forProperty("userEntity"), inits.get("userEntity")) : null;
    }

}

