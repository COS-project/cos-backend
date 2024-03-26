package com.cos.cercat.domain.mockExamResult.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserAnswers is a Querydsl query type for UserAnswers
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QUserAnswers extends BeanPath<UserAnswers> {

    private static final long serialVersionUID = -801794318L;

    public static final QUserAnswers userAnswers1 = new QUserAnswers("userAnswers1");

    public final ListPath<UserAnswer, QUserAnswer> userAnswers = this.<UserAnswer, QUserAnswer>createList("userAnswers", UserAnswer.class, QUserAnswer.class, PathInits.DIRECT2);

    public QUserAnswers(String variable) {
        super(UserAnswers.class, forVariable(variable));
    }

    public QUserAnswers(Path<? extends UserAnswers> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserAnswers(PathMetadata metadata) {
        super(UserAnswers.class, metadata);
    }

}

