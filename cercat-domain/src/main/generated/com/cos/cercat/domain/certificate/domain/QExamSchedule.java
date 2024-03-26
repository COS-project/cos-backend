package com.cos.cercat.domain.certificate.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QExamSchedule is a Querydsl query type for ExamSchedule
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QExamSchedule extends BeanPath<ExamSchedule> {

    private static final long serialVersionUID = -814679309L;

    public static final QExamSchedule examSchedule = new QExamSchedule("examSchedule");

    public final DateTimePath<java.time.LocalDateTime> applicationDeadlineDateTime = createDateTime("applicationDeadlineDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> applicationStartDateTime = createDateTime("applicationStartDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> examDateTime = createDateTime("examDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> ResultAnnouncementDateTime = createDateTime("ResultAnnouncementDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> resultAnnouncementDateTime = createDateTime("resultAnnouncementDateTime", java.time.LocalDateTime.class);

    public QExamSchedule(String variable) {
        super(ExamSchedule.class, forVariable(variable));
    }

    public QExamSchedule(Path<? extends ExamSchedule> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExamSchedule(PathMetadata metadata) {
        super(ExamSchedule.class, metadata);
    }

}

