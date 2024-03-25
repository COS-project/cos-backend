package com.cos.cercat.mockExamResult.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubjectResult is a Querydsl query type for SubjectResult
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubjectResult extends EntityPathBase<SubjectResult> {

    private static final long serialVersionUID = 237077939L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubjectResult subjectResult = new QSubjectResult("subjectResult");

    public final NumberPath<Integer> correctRate = createNumber("correctRate", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMockExamResult mockExamResult;

    public final NumberPath<Integer> numberOfCorrect = createNumber("numberOfCorrect", Integer.class);

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final com.cos.cercat.certificate.domain.QSubject subject;

    public final NumberPath<Long> totalTakenTime = createNumber("totalTakenTime", Long.class);

    public final QUserAnswers userAnswers;

    public QSubjectResult(String variable) {
        this(SubjectResult.class, forVariable(variable), INITS);
    }

    public QSubjectResult(Path<? extends SubjectResult> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubjectResult(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubjectResult(PathMetadata metadata, PathInits inits) {
        this(SubjectResult.class, metadata, inits);
    }

    public QSubjectResult(Class<? extends SubjectResult> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.mockExamResult = inits.isInitialized("mockExamResult") ? new QMockExamResult(forProperty("mockExamResult"), inits.get("mockExamResult")) : null;
        this.subject = inits.isInitialized("subject") ? new com.cos.cercat.certificate.domain.QSubject(forProperty("subject"), inits.get("subject")) : null;
        this.userAnswers = inits.isInitialized("userAnswers") ? new QUserAnswers(forProperty("userAnswers")) : null;
    }

}

