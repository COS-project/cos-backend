package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMockExam is a Querydsl query type for MockExamEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMockExam extends EntityPathBase<MockExam> {

    private static final long serialVersionUID = -583829697L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMockExam mockExam = new QMockExam("mockExam");

    public final com.cos.cercat.entity.QBaseTimeEntity _super = new com.cos.cercat.entity.QBaseTimeEntity(this);

    public final QCertificate certificate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> examYear = createNumber("examYear", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> round = createNumber("round", Integer.class);

    public final NumberPath<Long> timeLimit = createNumber("timeLimit", Long.class);

    public QMockExam(String variable) {
        this(MockExam.class, forVariable(variable), INITS);
    }

    public QMockExam(Path<? extends MockExam> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMockExam(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMockExam(PathMetadata metadata, PathInits inits) {
        this(MockExam.class, metadata, inits);
    }

    public QMockExam(Class<? extends MockExam> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.certificate = inits.isInitialized("certificate") ? new QCertificate(forProperty("certificate"), inits.get("certificate")) : null;
    }

}

