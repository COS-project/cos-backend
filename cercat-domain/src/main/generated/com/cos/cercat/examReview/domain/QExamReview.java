package com.cos.cercat.examReview.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExamReview is a Querydsl query type for ExamReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExamReview extends EntityPathBase<ExamReview> {

    private static final long serialVersionUID = 1993635102L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExamReview examReview = new QExamReview("examReview");

    public final com.cos.cercat.common.domain.QBaseTimeEntity _super = new com.cos.cercat.common.domain.QBaseTimeEntity(this);

    public final com.cos.cercat.certificate.domain.QCertificateExam certificateExam;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<ExamDifficulty> examDifficulty = createEnum("examDifficulty", ExamDifficulty.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> prepareMonths = createNumber("prepareMonths", Integer.class);

    public final com.cos.cercat.user.domain.QUser user;

    public QExamReview(String variable) {
        this(ExamReview.class, forVariable(variable), INITS);
    }

    public QExamReview(Path<? extends ExamReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExamReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExamReview(PathMetadata metadata, PathInits inits) {
        this(ExamReview.class, metadata, inits);
    }

    public QExamReview(Class<? extends ExamReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.certificateExam = inits.isInitialized("certificateExam") ? new com.cos.cercat.certificate.domain.QCertificateExam(forProperty("certificateExam"), inits.get("certificateExam")) : null;
        this.user = inits.isInitialized("user") ? new com.cos.cercat.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

