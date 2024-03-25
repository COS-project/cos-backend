package com.cos.cercat.mockExamResult.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMockExamResult is a Querydsl query type for MockExamResult
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMockExamResult extends EntityPathBase<MockExamResult> {

    private static final long serialVersionUID = -835754436L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMockExamResult mockExamResult = new QMockExamResult("mockExamResult");

    public final com.cos.cercat.common.domain.QBaseTimeEntity _super = new com.cos.cercat.common.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.cos.cercat.mockExam.domain.QMockExam mockExam;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> round = createNumber("round", Integer.class);

    public final QSubjectResults subjectResults;

    public final NumberPath<Integer> totalScore = createNumber("totalScore", Integer.class);

    public final com.cos.cercat.user.domain.QUser user;

    public QMockExamResult(String variable) {
        this(MockExamResult.class, forVariable(variable), INITS);
    }

    public QMockExamResult(Path<? extends MockExamResult> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMockExamResult(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMockExamResult(PathMetadata metadata, PathInits inits) {
        this(MockExamResult.class, metadata, inits);
    }

    public QMockExamResult(Class<? extends MockExamResult> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.mockExam = inits.isInitialized("mockExam") ? new com.cos.cercat.mockExam.domain.QMockExam(forProperty("mockExam"), inits.get("mockExam")) : null;
        this.subjectResults = inits.isInitialized("subjectResults") ? new QSubjectResults(forProperty("subjectResults")) : null;
        this.user = inits.isInitialized("user") ? new com.cos.cercat.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

