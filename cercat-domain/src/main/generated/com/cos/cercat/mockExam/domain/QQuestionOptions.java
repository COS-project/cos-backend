package com.cos.cercat.mockExam.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuestionOptions is a Querydsl query type for QuestionOptions
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QQuestionOptions extends BeanPath<QuestionOptions> {

    private static final long serialVersionUID = -1002025537L;

    public static final QQuestionOptions questionOptions1 = new QQuestionOptions("questionOptions1");

    public final ListPath<QuestionOption, QQuestionOption> questionOptions = this.<QuestionOption, QQuestionOption>createList("questionOptions", QuestionOption.class, QQuestionOption.class, PathInits.DIRECT2);

    public QQuestionOptions(String variable) {
        super(QuestionOptions.class, forVariable(variable));
    }

    public QQuestionOptions(Path<? extends QuestionOptions> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuestionOptions(PathMetadata metadata) {
        super(QuestionOptions.class, metadata);
    }

}

