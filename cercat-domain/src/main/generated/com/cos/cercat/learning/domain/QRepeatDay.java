package com.cos.cercat.learning.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRepeatDay is a Querydsl query type for RepeatDay
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRepeatDay extends EntityPathBase<RepeatDay> {

    private static final long serialVersionUID = -1210623629L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRepeatDay repeatDay = new QRepeatDay("repeatDay");

    public final QGoal goal;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<java.time.DayOfWeek> repeatDayOfWeek = createEnum("repeatDayOfWeek", java.time.DayOfWeek.class);

    public final EnumPath<RepeatType> repeatType = createEnum("repeatType", RepeatType.class);

    public QRepeatDay(String variable) {
        this(RepeatDay.class, forVariable(variable), INITS);
    }

    public QRepeatDay(Path<? extends RepeatDay> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRepeatDay(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRepeatDay(PathMetadata metadata, PathInits inits) {
        this(RepeatDay.class, metadata, inits);
    }

    public QRepeatDay(Class<? extends RepeatDay> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.goal = inits.isInitialized("goal") ? new QGoal(forProperty("goal"), inits.get("goal")) : null;
    }

}

