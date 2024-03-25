package com.cos.cercat.certificate.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPassingCriteria is a Querydsl query type for PassingCriteria
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPassingCriteria extends BeanPath<PassingCriteria> {

    private static final long serialVersionUID = -1737823823L;

    public static final QPassingCriteria passingCriteria = new QPassingCriteria("passingCriteria");

    public final NumberPath<Integer> practicalPassingCriteria = createNumber("practicalPassingCriteria", Integer.class);

    public final NumberPath<Integer> subjectPassingCriteria = createNumber("subjectPassingCriteria", Integer.class);

    public final NumberPath<Integer> totalAvgCriteria = createNumber("totalAvgCriteria", Integer.class);

    public QPassingCriteria(String variable) {
        super(PassingCriteria.class, forVariable(variable));
    }

    public QPassingCriteria(Path<? extends PassingCriteria> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPassingCriteria(PathMetadata metadata) {
        super(PassingCriteria.class, metadata);
    }

}

