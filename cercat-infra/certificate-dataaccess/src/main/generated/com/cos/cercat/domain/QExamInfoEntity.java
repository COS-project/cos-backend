package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QExamInfoEntity is a Querydsl query type for ExamInfoEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExamInfoEntity extends EntityPathBase<ExamInfoEntity> {

    private static final long serialVersionUID = 1458071974L;

    public static final QExamInfoEntity examInfoEntity = new QExamInfoEntity("examInfoEntity");

    public final DateTimePath<java.time.LocalDateTime> applicationDeadlineDateTime = createDateTime("applicationDeadlineDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> applicationStartDateTime = createDateTime("applicationStartDateTime", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.time.LocalDateTime> examDateTime = createDateTime("examDateTime", java.time.LocalDateTime.class);

    public final StringPath examEligibility = createString("examEligibility");

    public final StringPath examFormat = createString("examFormat");

    public final NumberPath<Integer> examYear = createNumber("examYear", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> practicalExamFee = createNumber("practicalExamFee", Integer.class);

    public final NumberPath<Long> practicalExamTimeLimit = createNumber("practicalExamTimeLimit", Long.class);

    public final NumberPath<Integer> practicalPassingCriteria = createNumber("practicalPassingCriteria", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> resultAnnouncementDateTime = createDateTime("resultAnnouncementDateTime", java.time.LocalDateTime.class);

    public final NumberPath<Integer> round = createNumber("round", Integer.class);

    public final NumberPath<Integer> subjectPassingCriteria = createNumber("subjectPassingCriteria", Integer.class);

    public final StringPath subjectsInfo = createString("subjectsInfo");

    public final NumberPath<Integer> totalAvgCriteria = createNumber("totalAvgCriteria", Integer.class);

    public final NumberPath<Integer> writtenExamFee = createNumber("writtenExamFee", Integer.class);

    public final NumberPath<Long> writtenExamTimeLimit = createNumber("writtenExamTimeLimit", Long.class);

    public QExamInfoEntity(String variable) {
        super(ExamInfoEntity.class, forVariable(variable));
    }

    public QExamInfoEntity(Path<? extends ExamInfoEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExamInfoEntity(PathMetadata metadata) {
        super(ExamInfoEntity.class, metadata);
    }

}

