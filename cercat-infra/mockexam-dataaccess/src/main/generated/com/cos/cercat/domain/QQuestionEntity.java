package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuestionEntity is a Querydsl query type for QuestionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestionEntity extends EntityPathBase<QuestionEntity> {

    private static final long serialVersionUID = 1391181151L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuestionEntity questionEntity = new QQuestionEntity("questionEntity");

    public final NumberPath<Integer> correctOption = createNumber("correctOption", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMockExamEntity mockExamEntity;

    public final com.cos.cercat.entity.QImageEntity questionImageEntity;

    public final ListPath<QuestionOptionEntity, QQuestionOptionEntity> questionOptions = this.<QuestionOptionEntity, QQuestionOptionEntity>createList("questionOptions", QuestionOptionEntity.class, QQuestionOptionEntity.class, PathInits.DIRECT2);

    public final NumberPath<Integer> questionSeq = createNumber("questionSeq", Integer.class);

    public final StringPath questionText = createString("questionText");

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final QSubjectEntity subjectEntity;

    public QQuestionEntity(String variable) {
        this(QuestionEntity.class, forVariable(variable), INITS);
    }

    public QQuestionEntity(Path<? extends QuestionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuestionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuestionEntity(PathMetadata metadata, PathInits inits) {
        this(QuestionEntity.class, metadata, inits);
    }

    public QQuestionEntity(Class<? extends QuestionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.mockExamEntity = inits.isInitialized("mockExamEntity") ? new QMockExamEntity(forProperty("mockExamEntity"), inits.get("mockExamEntity")) : null;
        this.questionImageEntity = inits.isInitialized("questionImageEntity") ? new com.cos.cercat.entity.QImageEntity(forProperty("questionImageEntity")) : null;
        this.subjectEntity = inits.isInitialized("subjectEntity") ? new QSubjectEntity(forProperty("subjectEntity"), inits.get("subjectEntity")) : null;
    }

}

