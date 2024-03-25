package com.cos.cercat.mockExamResult.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserAnswer is a Querydsl query type for UserAnswer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserAnswer extends EntityPathBase<UserAnswer> {

    private static final long serialVersionUID = 108976735L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserAnswer userAnswer = new QUserAnswer("userAnswer");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isCorrect = createBoolean("isCorrect");

    public final BooleanPath isReviewed = createBoolean("isReviewed");

    public final com.cos.cercat.mockExam.domain.QQuestion question;

    public final NumberPath<Integer> selectOptionSeq = createNumber("selectOptionSeq", Integer.class);

    public final QSubjectResult subjectResult;

    public final NumberPath<Long> takenTime = createNumber("takenTime", Long.class);

    public final com.cos.cercat.user.domain.QUser user;

    public QUserAnswer(String variable) {
        this(UserAnswer.class, forVariable(variable), INITS);
    }

    public QUserAnswer(Path<? extends UserAnswer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserAnswer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserAnswer(PathMetadata metadata, PathInits inits) {
        this(UserAnswer.class, metadata, inits);
    }

    public QUserAnswer(Class<? extends UserAnswer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.question = inits.isInitialized("question") ? new com.cos.cercat.mockExam.domain.QQuestion(forProperty("question"), inits.get("question")) : null;
        this.subjectResult = inits.isInitialized("subjectResult") ? new QSubjectResult(forProperty("subjectResult"), inits.get("subjectResult")) : null;
        this.user = inits.isInitialized("user") ? new com.cos.cercat.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

