package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QExamTimeLimit is a Querydsl query type for ExamTimeLimit
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QExamTimeLimit extends BeanPath<ExamTimeLimit> {

    private static final long serialVersionUID = -2111467815L;

    public static final QExamTimeLimit examTimeLimit = new QExamTimeLimit("examTimeLimit");

    public final NumberPath<Long> practicalExamTimeLimit = createNumber("practicalExamTimeLimit", Long.class);

    public final NumberPath<Long> writtenExamTimeLimit = createNumber("writtenExamTimeLimit", Long.class);

    public QExamTimeLimit(String variable) {
        super(ExamTimeLimit.class, forVariable(variable));
    }

    public QExamTimeLimit(Path<? extends ExamTimeLimit> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExamTimeLimit(PathMetadata metadata) {
        super(ExamTimeLimit.class, metadata);
    }

}

