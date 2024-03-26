package com.cos.cercat.domain.certificate.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInterestCertificate is a Querydsl query type for InterestCertificate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInterestCertificate extends EntityPathBase<InterestCertificate> {

    private static final long serialVersionUID = -995697072L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInterestCertificate interestCertificate = new QInterestCertificate("interestCertificate");

    public final com.cos.cercat.domain.common.domain.QBaseTimeEntity _super = new com.cos.cercat.domain.common.domain.QBaseTimeEntity(this);

    public final QCertificate certificate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.cos.cercat.domain.certificate.domain.embededId.QInterestCertificatePK interestCertificatePK;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final EnumPath<InterestPriority> priority = createEnum("priority", InterestPriority.class);

    public final com.cos.cercat.domain.user.domain.QUser user;

    public QInterestCertificate(String variable) {
        this(InterestCertificate.class, forVariable(variable), INITS);
    }

    public QInterestCertificate(Path<? extends InterestCertificate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInterestCertificate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInterestCertificate(PathMetadata metadata, PathInits inits) {
        this(InterestCertificate.class, metadata, inits);
    }

    public QInterestCertificate(Class<? extends InterestCertificate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.certificate = inits.isInitialized("certificate") ? new QCertificate(forProperty("certificate"), inits.get("certificate")) : null;
        this.interestCertificatePK = inits.isInitialized("interestCertificatePK") ? new com.cos.cercat.domain.certificate.domain.embededId.QInterestCertificatePK(forProperty("interestCertificatePK")) : null;
        this.user = inits.isInitialized("user") ? new com.cos.cercat.domain.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

