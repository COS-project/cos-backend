package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QExamFee is a Querydsl query type for ExamFee
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QExamFee extends BeanPath<ExamFee> {

    private static final long serialVersionUID = -905452111L;

    public static final QExamFee examFee = new QExamFee("examFee");

    public final NumberPath<Integer> practicalExamFee = createNumber("practicalExamFee", Integer.class);

    public final NumberPath<Integer> writtenExamFee = createNumber("writtenExamFee", Integer.class);

    public QExamFee(String variable) {
        super(ExamFee.class, forVariable(variable));
    }

    public QExamFee(Path<? extends ExamFee> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExamFee(PathMetadata metadata) {
        super(ExamFee.class, metadata);
    }

}

