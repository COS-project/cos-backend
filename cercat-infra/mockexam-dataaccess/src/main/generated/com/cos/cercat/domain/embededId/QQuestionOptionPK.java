package com.cos.cercat.domain.embededId;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQuestionOptionPK is a Querydsl query type for QuestionOptionPK
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QQuestionOptionPK extends BeanPath<QuestionOptionPK> {

    private static final long serialVersionUID = -1620372409L;

    public static final QQuestionOptionPK questionOptionPK = new QQuestionOptionPK("questionOptionPK");

    public final NumberPath<Integer> optionSequence = createNumber("optionSequence", Integer.class);

    public final NumberPath<Long> questionId = createNumber("questionId", Long.class);

    public QQuestionOptionPK(String variable) {
        super(QuestionOptionPK.class, forVariable(variable));
    }

    public QQuestionOptionPK(Path<? extends QuestionOptionPK> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuestionOptionPK(PathMetadata metadata) {
        super(QuestionOptionPK.class, metadata);
    }

}

