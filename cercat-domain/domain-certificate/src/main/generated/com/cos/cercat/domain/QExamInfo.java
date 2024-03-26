package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExamInfo is a Querydsl query type for ExamInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExamInfo extends EntityPathBase<ExamInfo> {

    private static final long serialVersionUID = 1995853795L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExamInfo examInfo = new QExamInfo("examInfo");

    public final StringPath description = createString("description");

    public final StringPath examEligibility = createString("examEligibility");

    public final QExamFee examFee;

    public final StringPath examFormat = createString("examFormat");

    public final QExamSchedule examSchedule;

    public final QExamTimeLimit examTimeLimit;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPassingCriteria passingCriteria;

    public final StringPath subjectsInfo = createString("subjectsInfo");

    public QExamInfo(String variable) {
        this(ExamInfo.class, forVariable(variable), INITS);
    }

    public QExamInfo(Path<? extends ExamInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExamInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExamInfo(PathMetadata metadata, PathInits inits) {
        this(ExamInfo.class, metadata, inits);
    }

    public QExamInfo(Class<? extends ExamInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.examFee = inits.isInitialized("examFee") ? new QExamFee(forProperty("examFee")) : null;
        this.examSchedule = inits.isInitialized("examSchedule") ? new QExamSchedule(forProperty("examSchedule")) : null;
        this.examTimeLimit = inits.isInitialized("examTimeLimit") ? new QExamTimeLimit(forProperty("examTimeLimit")) : null;
        this.passingCriteria = inits.isInitialized("passingCriteria") ? new QPassingCriteria(forProperty("passingCriteria")) : null;
    }

}

