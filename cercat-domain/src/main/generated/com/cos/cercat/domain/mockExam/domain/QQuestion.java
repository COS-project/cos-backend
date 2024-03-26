package com.cos.cercat.domain.mockExam.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuestion is a Querydsl query type for Question
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestion extends EntityPathBase<Question> {

    private static final long serialVersionUID = -593973983L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuestion question = new QQuestion("question");

    public final NumberPath<Integer> correctOption = createNumber("correctOption", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMockExam mockExam;

    public final com.cos.cercat.domain.common.domain.QImage questionImage;

    public final QQuestionOptions questionOptions;

    public final NumberPath<Integer> questionSeq = createNumber("questionSeq", Integer.class);

    public final StringPath questionText = createString("questionText");

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final com.cos.cercat.domain.certificate.domain.QSubject subject;

    public QQuestion(String variable) {
        this(Question.class, forVariable(variable), INITS);
    }

    public QQuestion(Path<? extends Question> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuestion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuestion(PathMetadata metadata, PathInits inits) {
        this(Question.class, metadata, inits);
    }

    public QQuestion(Class<? extends Question> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.mockExam = inits.isInitialized("mockExam") ? new QMockExam(forProperty("mockExam"), inits.get("mockExam")) : null;
        this.questionImage = inits.isInitialized("questionImage") ? new com.cos.cercat.domain.common.domain.QImage(forProperty("questionImage")) : null;
        this.questionOptions = inits.isInitialized("questionOptions") ? new QQuestionOptions(forProperty("questionOptions")) : null;
        this.subject = inits.isInitialized("subject") ? new com.cos.cercat.domain.certificate.domain.QSubject(forProperty("subject"), inits.get("subject")) : null;
    }

}

