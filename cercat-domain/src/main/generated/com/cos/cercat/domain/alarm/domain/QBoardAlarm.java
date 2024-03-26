package com.cos.cercat.domain.alarm.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardAlarm is a Querydsl query type for BoardAlarm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardAlarm extends EntityPathBase<BoardAlarm> {

    private static final long serialVersionUID = 437489442L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardAlarm boardAlarm = new QBoardAlarm("boardAlarm");

    public final QAlarm _super;

    //inherited
    public final EnumPath<AlarmType> alarmType;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    public final com.cos.cercat.domain.user.domain.QUser fromUser;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final BooleanPath isRead;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt;

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    // inherited
    public final com.cos.cercat.domain.user.domain.QUser receiveUser;

    public QBoardAlarm(String variable) {
        this(BoardAlarm.class, forVariable(variable), INITS);
    }

    public QBoardAlarm(Path<? extends BoardAlarm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardAlarm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardAlarm(PathMetadata metadata, PathInits inits) {
        this(BoardAlarm.class, metadata, inits);
    }

    public QBoardAlarm(Class<? extends BoardAlarm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QAlarm(type, metadata, inits);
        this.alarmType = _super.alarmType;
        this.createdAt = _super.createdAt;
        this.fromUser = inits.isInitialized("fromUser") ? new com.cos.cercat.domain.user.domain.QUser(forProperty("fromUser"), inits.get("fromUser")) : null;
        this.id = _super.id;
        this.isRead = _super.isRead;
        this.modifiedAt = _super.modifiedAt;
        this.receiveUser = _super.receiveUser;
    }

}

