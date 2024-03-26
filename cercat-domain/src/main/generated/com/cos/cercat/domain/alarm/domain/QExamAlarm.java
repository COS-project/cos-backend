package com.cos.cercat.domain.alarm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExamAlarm is a Querydsl query type for ExamAlarm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExamAlarm extends EntityPathBase<ExamAlarm> {

    private static final long serialVersionUID = 1374362235L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExamAlarm examAlarm = new QExamAlarm("examAlarm");

    public final QAlarm _super;

    //inherited
    public final EnumPath<AlarmType> alarmType;

    public final com.cos.cercat.domain.certificate.domain.QCertificateExam certificateExam;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final BooleanPath isRead;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt;

    // inherited
    public final com.cos.cercat.domain.user.domain.QUser receiveUser;

    public QExamAlarm(String variable) {
        this(ExamAlarm.class, forVariable(variable), INITS);
    }

    public QExamAlarm(Path<? extends ExamAlarm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExamAlarm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExamAlarm(PathMetadata metadata, PathInits inits) {
        this(ExamAlarm.class, metadata, inits);
    }

    public QExamAlarm(Class<? extends ExamAlarm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QAlarm(type, metadata, inits);
        this.alarmType = _super.alarmType;
        this.certificateExam = inits.isInitialized("certificateExam") ? new com.cos.cercat.domain.certificate.domain.QCertificateExam(forProperty("certificateExam"), inits.get("certificateExam")) : null;
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.isRead = _super.isRead;
        this.modifiedAt = _super.modifiedAt;
        this.receiveUser = _super.receiveUser;
    }

}

