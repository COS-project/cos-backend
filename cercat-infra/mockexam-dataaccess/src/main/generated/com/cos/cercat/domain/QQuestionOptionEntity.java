package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuestionOptionEntity is a Querydsl query type for QuestionOptionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestionOptionEntity extends EntityPathBase<QuestionOptionEntity> {

    private static final long serialVersionUID = 2001484084L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuestionOptionEntity questionOptionEntity = new QQuestionOptionEntity("questionOptionEntity");

    public final StringPath optionContent = createString("optionContent");

    public final com.cos.cercat.entity.QImageEntity optionImageEntity;

    public final QQuestionEntity questionEntity;

    public final com.cos.cercat.domain.embededId.QQuestionOptionPK questionOptionPK;

    public QQuestionOptionEntity(String variable) {
        this(QuestionOptionEntity.class, forVariable(variable), INITS);
    }

    public QQuestionOptionEntity(Path<? extends QuestionOptionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuestionOptionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuestionOptionEntity(PathMetadata metadata, PathInits inits) {
        this(QuestionOptionEntity.class, metadata, inits);
    }

    public QQuestionOptionEntity(Class<? extends QuestionOptionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.optionImageEntity = inits.isInitialized("optionImageEntity") ? new com.cos.cercat.entity.QImageEntity(forProperty("optionImageEntity")) : null;
        this.questionEntity = inits.isInitialized("questionEntity") ? new QQuestionEntity(forProperty("questionEntity"), inits.get("questionEntity")) : null;
        this.questionOptionPK = inits.isInitialized("questionOptionPK") ? new com.cos.cercat.domain.embededId.QQuestionOptionPK(forProperty("questionOptionPK")) : null;
    }

}

