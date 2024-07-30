package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserAnswerEntity is a Querydsl query type for UserAnswerEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserAnswerEntity extends EntityPathBase<UserAnswerEntity> {

    private static final long serialVersionUID = 309444898L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserAnswerEntity userAnswerEntity = new QUserAnswerEntity("userAnswerEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isCorrect = createBoolean("isCorrect");

    public final BooleanPath isReviewed = createBoolean("isReviewed");

    public final QQuestionEntity questionEntity;

    public final NumberPath<Integer> selectOptionSeq = createNumber("selectOptionSeq", Integer.class);

    public final QSubjectResultEntity subjectResultEntity;

    public final NumberPath<Long> takenTime = createNumber("takenTime", Long.class);

    public final QUserEntity userEntity;

    public QUserAnswerEntity(String variable) {
        this(UserAnswerEntity.class, forVariable(variable), INITS);
    }

    public QUserAnswerEntity(Path<? extends UserAnswerEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserAnswerEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserAnswerEntity(PathMetadata metadata, PathInits inits) {
        this(UserAnswerEntity.class, metadata, inits);
    }

    public QUserAnswerEntity(Class<? extends UserAnswerEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.questionEntity = inits.isInitialized("questionEntity") ? new QQuestionEntity(forProperty("questionEntity"), inits.get("questionEntity")) : null;
        this.subjectResultEntity = inits.isInitialized("subjectResultEntity") ? new QSubjectResultEntity(forProperty("subjectResultEntity"), inits.get("subjectResultEntity")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new QUserEntity(forProperty("userEntity"), inits.get("userEntity")) : null;
    }

}

