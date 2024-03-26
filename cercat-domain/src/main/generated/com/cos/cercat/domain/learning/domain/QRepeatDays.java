package com.cos.cercat.domain.learning.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRepeatDays is a Querydsl query type for RepeatDays
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QRepeatDays extends BeanPath<RepeatDays> {

    private static final long serialVersionUID = -559992286L;

    public static final QRepeatDays repeatDays1 = new QRepeatDays("repeatDays1");

    public final ListPath<RepeatDay, QRepeatDay> repeatDays = this.<RepeatDay, QRepeatDay>createList("repeatDays", RepeatDay.class, QRepeatDay.class, PathInits.DIRECT2);

    public QRepeatDays(String variable) {
        super(RepeatDays.class, forVariable(variable));
    }

    public QRepeatDays(Path<? extends RepeatDays> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRepeatDays(PathMetadata metadata) {
        super(RepeatDays.class, metadata);
    }

}

