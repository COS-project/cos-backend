package com.cos.cercat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInterestCertificateEntity is a Querydsl query type for InterestCertificateEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInterestCertificateEntity extends EntityPathBase<InterestCertificateEntity> {

    private static final long serialVersionUID = 355265178L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInterestCertificateEntity interestCertificateEntity = new QInterestCertificateEntity("interestCertificateEntity");

    public final com.cos.cercat.entity.QBaseTimeEntity _super = new com.cos.cercat.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.cos.cercat.domain.embededId.QInterestCertificatePK interestCertificatePK;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final EnumPath<com.cos.cercat.domain.certificate.InterestPriority> priority = createEnum("priority", com.cos.cercat.domain.certificate.InterestPriority.class);

    public QInterestCertificateEntity(String variable) {
        this(InterestCertificateEntity.class, forVariable(variable), INITS);
    }

    public QInterestCertificateEntity(Path<? extends InterestCertificateEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInterestCertificateEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInterestCertificateEntity(PathMetadata metadata, PathInits inits) {
        this(InterestCertificateEntity.class, metadata, inits);
    }

    public QInterestCertificateEntity(Class<? extends InterestCertificateEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.interestCertificatePK = inits.isInitialized("interestCertificatePK") ? new com.cos.cercat.domain.embededId.QInterestCertificatePK(forProperty("interestCertificatePK"), inits.get("interestCertificatePK")) : null;
    }

}

